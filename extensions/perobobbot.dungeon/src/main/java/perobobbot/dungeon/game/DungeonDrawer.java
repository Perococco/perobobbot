package perobobbot.dungeon.game;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.dungeon.game.entity.Entity;
import perobobbot.rendering.Renderer;
import perobobbot.rendering.Size;
import perobobbot.rendering.tile.Tile;
import perococco.jdgen.api.Map;
import perococco.jdgen.api.Position;
import perococco.jdgen.api.Transformation;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class DungeonDrawer {

    public static final int HALF_WIDTH_IN_TILES = 16;
    public static final int WIDTH_IN_TILES = 2 * HALF_WIDTH_IN_TILES + 1;
    public static final int HALF_HEIGHT_IN_TILES = 20;
    public static final int HEIGHT_IN_TILES = 2 * HALF_HEIGHT_IN_TILES + 1;
    public static final int MAX_TILE_SIZE = 64;

    public static void render(@NonNull Dungeon dungeon,
                              @NonNull Renderer renderer,
                              @NonNull Size size) {

        final var centerPosition = dungeon.getPlayerPosition();

        final var dx = centerPosition.getX() - HALF_WIDTH_IN_TILES;
        final var dy = centerPosition.getY() - HALF_HEIGHT_IN_TILES;

        final Map<DungeonCell> offsetedMap = dungeon.getMap().setTransformation(Transformation.offset(dx,dy));

        renderer.withPrivateTransform(r -> new DungeonDrawer(offsetedMap, dungeon::entities, r, size).render());
    }

    private final @NonNull Map<DungeonCell> map;
    private final @NonNull Supplier<Stream<Entity>> entitySupplier;
    private final @NonNull Renderer renderer;
    private final @NonNull Size size;

    private double tileSize = MAX_TILE_SIZE;
    private double scale;

    public void render() {
        this.computeScale();
        this.translateRenderer();
        this.scaleRenderer();

        this.drawAllPosition();
    }

    private void computeScale() {
        this.tileSize = Math.min(MAX_TILE_SIZE, size.getWidth() / WIDTH_IN_TILES);
        this.scale = size.getWidth()/(tileSize*WIDTH_IN_TILES);
    }

    private void translateRenderer() {
        final var height = tileSize * (HEIGHT_IN_TILES + 1);
        renderer.translate(0, size.getHeight() - height - 10);
    }

    private void scaleRenderer() {
        renderer.scale(scale);
    }


    private void drawAllPosition() {
        Holder.POSITIONS_TO_DRAW.forEach(this::drawOnePosition);
    }

    private void drawOnePosition(@NonNull Position position) {
        if (map.isOutside(position)) {
            return;
        }
        final var cell = map.getCellAt(position);

        Stream.of(Layer.FLOOR, Layer.WALL)
              .flatMap(cell::getTile)
              .forEach(t -> drawTile(t,position));
    }

    private void drawTile(@NonNull Tile tile, @NonNull Position position) {
        tile.render(renderer, position.getX() * tileSize, position.getY() * tileSize, tileSize/16);
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
