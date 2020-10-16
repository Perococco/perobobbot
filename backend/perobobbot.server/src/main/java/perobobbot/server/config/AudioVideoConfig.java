package perobobbot.server.config;

import lombok.experimental.UtilityClass;
import perobobbot.overlay.FrameRate;

@UtilityClass
public class AudioVideoConfig {

    public static final int AUDIO_SAMPLING_RATE = 48_000;

    public static final int OVERLAY_WIDTH = 1920;

    public static final int OVERLAY_HEIGHT = 1080;

    public static final FrameRate OVERLAY_FRAME_RATE = FrameRate.FPS_60;
}
