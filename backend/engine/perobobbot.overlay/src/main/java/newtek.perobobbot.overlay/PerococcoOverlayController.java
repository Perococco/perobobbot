package newtek.perobobbot.overlay;

import com.google.common.collect.ImmutableList;
import com.walker.devolay.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.experimental.Delegate;
import lombok.extern.log4j.Log4j2;
import perobobbot.common.lang.ListTool;
import perobobbot.common.lang.Looper;
import perobobbot.common.lang.Subscription;
import perobobbot.common.sound.SoundManager;
import perobobbot.common.sound.SoundRegistry;
import perobobbot.overlay.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

@RequiredArgsConstructor
@Log4j2
public class PerococcoOverlayController implements OverlayController, Overlay {

    static {
        Devolay.loadLibraries();
    }

    private final Drawer drawer;

    private final Sender sender;

    private final boolean auto;

    private ImmutableList<OverlayClient> drawers = ImmutableList.of();

    private final String ndiName;

    @Getter
    private final int width;

    @Getter
    private final int height;

    @NonNull
    @Getter
    private final FrameRate frameRate;

    @Delegate(types = {SoundRegistry.class})
    private final SoundManager soundManager;

    public PerococcoOverlayController(String ndiName, int width, int height, @NonNull FrameRate frameRate, @NonNull SoundManager soundManager) {
        this.auto = false;
        this.width = width;
        this.height = height;
        this.frameRate = frameRate;
        this.ndiName = ndiName;
        this.soundManager = soundManager;
        final NDIConfig ndiConfig = new NDIConfig(width, height, DevolayFrameFourCCType.RGBA, frameRate, soundManager.getSampleRate(), soundManager.getNbChannels());
        final NDIData ndiData = new NDIData(ndiConfig, soundManager,3);

        this.drawer = new Drawer(ndiData, frameRate.getDeltaT());
        this.sender = new Sender(new DevolaySender(ndiName,null,true,false), ndiConfig.createNDIFrame(),ndiConfig.createNDIAudioFrame(), ndiData);
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

        private final double dt;

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
            try (OverlayIteration iteration = this.createOverlayIteration()) {
                iteration.clearDrawing();
                drawers.forEach(d -> renderDrawer(d, iteration));
                ndiData.copyDataToFreeBuffers(iteration.getIterationCount());
            }
            return IterationCommand.CONTINUE;
        }

        private void renderDrawer(@NonNull OverlayClient client, @NonNull OverlayIteration iteration) {
            client.render(iteration);
        }

        private OverlayIteration createOverlayIteration() {
            return SimpleOverlayIteration.builder()
                                         .deltaTime(dt)
                                         .iterationCount(iterationCount)
                                         .time(time)
                                         .soundContext(ndiData.getSoundContext())
                                         .drawingContext(ndiData.createDrawingContext())
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
            this.fpsCounter = FPSCounter.toLogger(LOG);
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
            if (frameCount % 30 == 0) {
                fpsCounter.display(30);
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
        client.initialize(this);
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
