package perobobbot.overlay.newtek;

import com.google.common.collect.ImmutableList;
import com.walker.devolay.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.experimental.Delegate;
import lombok.extern.log4j.Log4j2;
import perobobbot.lang.ListTool;
import perobobbot.lang.Looper;
import perobobbot.lang.Subscription;
import perobobbot.overlay.api.*;
import perobobbot.rendering.Size;
import perobobbot.sound.SoundManager;
import perobobbot.sound.SoundRegistry;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

@RequiredArgsConstructor
@Log4j2
public class NewtekOverlayController implements OverlayController, Overlay {

    static {
        Devolay.loadLibraries();
    }

    private final @NonNull Drawer drawer;

    private final @NonNull Sender sender;

    private final boolean auto;

    private final @NonNull String ndiName;

    @Getter
    private final @NonNull Size overlaySize;

    @Getter
    private final @NonNull FrameRate frameRate;

    @Delegate(types = {SoundRegistry.class})
    private final @NonNull SoundManager soundManager;

    private ImmutableList<OverlayClient> drawers = ImmutableList.of();


    public NewtekOverlayController(@NonNull String ndiName, @NonNull Size overlaySize, @NonNull FrameRate frameRate, @NonNull SoundManager soundManager) {
        this.auto = false;
        this.overlaySize = overlaySize;
        this.frameRate = frameRate;
        this.ndiName = ndiName;
        this.soundManager = soundManager;
        final NDIConfig ndiConfig = new NDIConfig(overlaySize, DevolayFrameFourCCType.RGBA, frameRate, soundManager.getSampleRate(), soundManager.getNbChannels());
        final NDIData ndiData = new NDIData(ndiConfig, soundManager,3);

        this.drawer = new Drawer(ndiData, frameRate.getDeltaT());
        this.sender = new Sender(new DevolaySender(ndiName,null,true,false), ndiConfig.createNDIFrame(),ndiConfig.createNDIAudioFrame(), ndiData);
    }

    @Override
    public @NonNull String getImplementationName() {
        return "newtek";
    }

    @Override
    @Synchronized
    public void start() {
        drawer.start();
        sender.start();
    }

    @Override
    @Synchronized
    public void stop() {
        sender.requestStop();
        drawer.requestStop();
    }

    @RequiredArgsConstructor
    private class Drawer extends Looper {

        @NonNull
        private final NDIData ndiData;

        /**
         * The time between each frames in second (1./FrameRate)
         */
        private final double dt;

        /**
         * The time in second
         */
        private double time;

        private long iterationCount = 0;

        @Override
        protected void beforeLooping() {
            LOG.info("Start overlay controller '{}'", ndiName);
            super.beforeLooping();
            this.time = 0;
            this.iterationCount = 0;
        }

        @Override
        protected void afterLooping() {
            super.afterLooping();
            ndiData.release();
            LOG.info("Stop  overlay controller '{}'", ndiName);
        }

        @Override
        protected @NonNull IterationCommand performOneIteration() throws Exception {
            this.time += dt;
            this.iterationCount++;
            final ImmutableList<OverlayClient> clients = drawers;
            final OverlayIteration iteration = this.createOverlayIteration();
            try {
                iteration.clearOverlay();
                clients.forEach(d -> renderDrawer(d, iteration));
                ndiData.copyDataToFreeBuffers(iteration.getIterationCount());
            } finally {
                iteration.getRenderer().close();
            }
            return IterationCommand.CONTINUE;
        }

        private void renderDrawer(@NonNull OverlayClient client, @NonNull OverlayIteration iteration) {
            client.render(iteration);
        }

        private @NonNull OverlayIteration createOverlayIteration() {
            return SimpleOverlayIteration.builder()
                                         .deltaTime(dt)
                                         .iterationCount(iterationCount)
                                         .time(time)
                                         .soundContext(ndiData.getSoundContext())
                                         .renderer(ndiData.createOverlayRenderer())
                                         .build();
        }
    }

    @NonNull
    private static class Sender extends Looper {

        @NonNull
        private final DevolaySender sender;

        @NonNull
        private final DevolayVideoFrame videoFrame;

        @NonNull
        private final DevolayAudioFrame audioFrame;

        @NonNull
        private final ByteBuffer audioBuffer;

        @NonNull
        private final NDIData ndiData;

        private final FPSCounter fpsCounter;

        private int frameCount = 0;

        public Sender(@NonNull DevolaySender sender,
                      @NonNull DevolayVideoFrame videoFrame,
                      @NonNull AudioFrame audioFrame,
                      @NonNull NDIData ndiData) {
            this.sender = sender;
            this.videoFrame = videoFrame;
            this.audioFrame = audioFrame.getFrame();
            this.audioBuffer = audioFrame.getData();
            this.ndiData = ndiData;
            this.fpsCounter = FPSCounter.toLogger();
        }

        @Override
        protected void beforeLooping() {
            super.beforeLooping();
            this.fpsCounter.start();
        }

        @Override
        protected void afterLooping() {
            super.afterLooping();
            this.sender.close();
            this.videoFrame.close();
        }

        @Override
        protected @NonNull IterationCommand performOneIteration() throws Exception {
            final NDIBuffers data = ndiData.takePendingBuffer();

            audioFrame.setSamples(data.nbAudioSamples);
            copyAudioData(data.audio,audioBuffer);
            videoFrame.setData(data.video);

            sender.sendAudioFrame(audioFrame);
            sender.sendVideoFrame(videoFrame);

            ndiData.releaseBuffer(data);

            frameCount++;
            if (frameCount % 60 == 0) {
                fpsCounter.display(60);
            }
            return IterationCommand.CONTINUE;
        }

        private void copyAudioData(float[][] audio, @NonNull ByteBuffer audioBuffer) {
            final FloatBuffer floatBuffer = audioBuffer.asFloatBuffer();
            floatBuffer.position(0);
            for (float[] floats : audio) {
                floatBuffer.put(floats);
            }
            floatBuffer.flip();
        }
    }


    @Override
    @Synchronized
    public @NonNull Subscription addClient(@NonNull OverlayClient client) {
        final OverlayClient safeClient = new SafeOverlayClient(client);
        safeClient.initialize(this);
        this.drawers = ListTool.addFirst(drawers, safeClient);
        if (auto) {
            this.start();
        }
        return () -> removeDrawer(safeClient);
    }

    @Synchronized
    private void removeDrawer(@NonNull OverlayClient client) {
        this.drawers = ListTool.removeFirst(drawers, client);
        client.dispose(this);
        if (this.drawers.isEmpty() && auto) {
            this.stop();
        }
    }

}
