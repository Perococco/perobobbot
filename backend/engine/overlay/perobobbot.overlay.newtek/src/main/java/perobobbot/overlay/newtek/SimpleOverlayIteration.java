package perobobbot.overlay.newtek;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.overlay.api.OverlayIteration;
import perobobbot.overlay.api.OverlayRenderer;
import perobobbot.overlay.api.SoundContext;

@RequiredArgsConstructor
@Builder
public class SimpleOverlayIteration implements OverlayIteration {

    @Getter
    private final double time;

    @Getter
    private final double deltaTime;

    @Getter
    private final long iterationCount;

    @Getter
    private final @NonNull OverlayRenderer overlayRenderer;

    @Getter
    private final @NonNull SoundContext soundContext;

}
