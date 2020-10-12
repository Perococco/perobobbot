package newtek.perobobbot.overlay;

import com.google.common.collect.ImmutableList;
import com.walker.devolay.Devolay;
import com.walker.devolay.DevolayFrameFourCCType;
import com.walker.devolay.DevolaySender;
import com.walker.devolay.DevolayVideoFrame;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import perobobbot.common.lang.ListTool;
import perobobbot.common.lang.Looper;
import perobobbot.common.lang.Subscription;
import perobobbot.common.lang.ThrowableTool;
import perobobbot.overlay.*;

import java.nio.ByteBuffer;

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

    public PerococcoOverlayController(String ndiName, int width, int height, @NonNull FrameRate frameRate) {
        this.auto = false;
        this.width = width;
        this.height = height;
        this.frameRate = frameRate;
        this.ndiName = ndiName;
        final NDIConfig ndiConfig = new NDIConfig(width, height, DevolayFrameFourCCType.RGBA, frameRate);
        final NDIData ndiData = new NDIData(ndiConfig, 3);

        this.drawer = new Drawer(ndiData, frameRate.getDeltaT());
        this.sender = new Sender(new DevolaySender(ndiName), ndiConfig.createNDIFrame(), ndiData);
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
            LOG.info("Start overlay controller '{}'",ndiName);
            super.beforeLooping();
            this.time = 0;
            this.iterationCount = 0;
        }

        @Override
        protected void afterLooping() {
            super.afterLooping();
            LOG.info("Stop  overlay controller '{}'",ndiName);
        }

        @Override
        protected @NonNull IterationCommand performOneIteration() throws Exception {
            this.time+=dt;
            this.iterationCount++;
            try (OverlayIteration iteration = this.createOverlayIteration()) {
                iteration.clearDrawing();
                drawers.forEach(d -> renderDrawer(d,iteration));
                ndiData.copyImageToFreeBuffer();
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
                    .drawingContext(ndiData.createDrawingContext())
                    .build();
        }
    }

    @NonNull
    private static class Sender extends Looper {

        @NonNull
        private final DevolaySender sender;

        @NonNull
        private final DevolayVideoFrame frame;

        @NonNull
        private final NDIData ndiData;

        private final FPSCounter fpsCounter;

        private int frameCount = 0;

        public Sender(@NonNull DevolaySender sender,
                      @NonNull DevolayVideoFrame frame,
                      @NonNull NDIData ndiData) {
            this.sender = sender;
            this.frame = frame;
            this.ndiData = ndiData;
            this.fpsCounter = FPSCounter.toStdOut();//toLogger(LOG);
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
            this.frame.close();
        }

        @Override
        protected @NonNull IterationCommand performOneIteration() throws Exception {
            final ByteBuffer data = ndiData.takePendingBuffer();
            frame.setData(data);
            sender.sendVideoFrame(frame);
            ndiData.releaseBuffer(data);
            frameCount++;
            if (frameCount % 30 == 0) {
                fpsCounter.display(30);
            }
            return IterationCommand.CONTINUE;
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
