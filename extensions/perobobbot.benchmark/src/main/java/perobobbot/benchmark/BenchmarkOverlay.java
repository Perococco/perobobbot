package perobobbot.benchmark;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.overlay.api.OverlayClient;
import perobobbot.overlay.api.OverlayIteration;
import perobobbot.rendering.ScreenSize;

import java.util.stream.IntStream;

@RequiredArgsConstructor
public class BenchmarkOverlay implements OverlayClient {

    public static @NonNull BenchmarkOverlay create(int nbPucks, int puckRadius, @NonNull ScreenSize overlaySize) {
        final var factory = new PuckFactory(puckRadius,overlaySize);
        final var pucks = IntStream.range(0,nbPucks).mapToObj(factory::create).collect(ImmutableList.toImmutableList());
        return new BenchmarkOverlay(pucks);
    }

    private final @NonNull ImmutableList<Puck> pucks;

    @Override
    public void render(@NonNull OverlayIteration iteration) {
        final var render = iteration.getRenderer();
        final var size = render.getDrawingSize();
        pucks.forEach(p -> p.update(iteration.getDeltaTime())
                            .wrap(size)
                            .draw(render));
    }
}
