package perobobbot.dungeon.game;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.rendering.Renderer;
import perobobbot.rendering.Size;
import perococco.jdgen.api.Map;
import perococco.jdgen.api.Position;
import perococco.jdgen.api.Transformation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class DungeonDrawer {

    public static final int HALF_WIDTH_IN_TILES = 4;
    public static final int WIDTH_IN_TILES = 2 * HALF_WIDTH_IN_TILES + 1;
    public static final int HALF_HEIGHT_IN_TILES = 5;
    public static final int HEIGHT_IN_TILES = 2 * HALF_HEIGHT_IN_TILES + 1;
    public static final int MAX_TILE_SIZE = 32;

    public static void render(@NonNull Map<DungeonCell> map,
                              @NonNull Position centerPosition,
                              @NonNull Renderer renderer,
                              @NonNull Size size) {

        final var dx = centerPosition.getX() - HALF_WIDTH_IN_TILES;
        final var dy = centerPosition.getY() - HALF_HEIGHT_IN_TILES;

        final Map<DungeonCell> offsetedMap = map.setTransformation(Transformation.offset(dx,dy));

        renderer.withPrivateTransform(r -> new DungeonDrawer(offsetedMap, r, size).render());
    }

    private final @NonNull Map<DungeonCell> map;
    private final @NonNull Renderer renderer;
    private final @NonNull Size size;

    private double tileSize = MAX_TILE_SIZE;
    private double scale;

    public void render() {
        this.computeScale();
        this.translateRenderer();
        this.scaleRenderer();
        Arrays.stream(Layer.values())
              .forEach(l -> {
                  Holder.POSITIONS_TO_DRAW.forEach(p -> drawTile(l,p));
              });
    }

    private void computeScale() {
        final var availableTileSize = size.getWidth() / WIDTH_IN_TILES;
        if (availableTileSize > MAX_TILE_SIZE) {
            this.scale = 1;
        } else {
            this.scale = size.getWidth() / (WIDTH_IN_TILES * MAX_TILE_SIZE);
        }

    }

    private void translateRenderer() {
        final var height = tileSize * (HEIGHT_IN_TILES + 1);
        renderer.translate(0, size.getHeight() - height - 10);
    }

    private void scaleRenderer() {
        renderer.scale(scale);
    }

    private void drawTile(@NonNull Layer layer, @NonNull Position position) {
        final var cell = map.getCellAt(position);

        cell.getTile(layer)
            .forEach(tile -> {
                tile.render(renderer, position.getX() * tileSize, position.getY() * tileSize, tileSize, tileSize);
            });

    }


    private static class Holder {

        private static final List<Position> POSITIONS_TO_DRAW;

        static {
            POSITIONS_TO_DRAW = IntStream.range(0, WIDTH_IN_TILES * HEIGHT_IN_TILES)
                                         .mapToObj(i -> new Position(i % WIDTH_IN_TILES, i / WIDTH_IN_TILES))
                                         .collect(Collectors.toList());
        }


    }
}
