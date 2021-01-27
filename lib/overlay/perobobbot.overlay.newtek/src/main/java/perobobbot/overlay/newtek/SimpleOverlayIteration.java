package perobobbot.overlay.newtek;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.overlay.api.OverlayIteration;
import perobobbot.overlay.api.SoundContext;
import perobobbot.rendering.Renderer;
import perobobbot.timeline.PropertyFactory;

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
    private final @NonNull Renderer renderer;

    @Getter
    private final @NonNull SoundContext soundContext;

    @Delegate
    private final @NonNull PropertyFactory propertyFactory;

}
