package perobobbot.dungeon;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.fp.Consumer2;
import perobobbot.overlay.api.Overlay;
import perobobbot.overlay.api.OverlayClient;
import perobobbot.overlay.api.OverlayIteration;
import perobobbot.rendering.Region;
import perococco.jdgen.api.Cell;
import perococco.jdgen.api.IntPoint;
import perococco.jdgen.api.Map;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class DungeonOverlay implements OverlayClient {

    private final @NonNull Map map;

    private final @NonNull Region region;

    private final AtomicReference<BufferedImage> mapAsImage = new AtomicReference<>(null);

    @Override
    public void initialize(@NonNull Overlay overlay) {
        new Thread(new ImageDrawer(map,mapAsImage)).start();
    }

    @Override
    public void render(@NonNull OverlayIteration iteration) {
        final var renderer = iteration.getRenderer();
        final var image = mapAsImage.get();
        if (image == null) {
            return;
        }

        renderer.translate(region.getX(),region.getY());
        renderer.drawImage(image,0,0);
    }

    @RequiredArgsConstructor
    private static class ImageDrawer implements Runnable {
        private final @NonNull Map<Cell> map;
        private final AtomicReference<BufferedImage> mapAsImage;

        private final int tileSize=5;

        @Override
        public void run() {
            final var size = map.getSize();
            final var image = new BufferedImage(size.getWidth()*tileSize,size.getHeight()*tileSize,BufferedImage.TYPE_4BYTE_ABGR);

            final var graphics = (Graphics2D)image.getGraphics();
            final Consumer<IntPoint> drawer = Consumer2.toConsumer2(this::putPixel).f2(graphics);

            graphics.setBackground(new Color(0,0,0,0));
            graphics.clearRect(0,0,image.getWidth(),image.getHeight());

            map.allMapPositions()
               .forEach(drawer);

            mapAsImage.set(image);
        }

        private void putPixel(IntPoint position, Graphics2D graphics2D) {
            final var type = map.getCellAt(position).getType();
            final var color = switch (type) {
                case EMPTY -> null;
                case WALL -> Color.BLACK;
                case ROOM_FLOOR -> Color.RED;
                case CORRIDOR_FLOOR -> Color.GREEN;
                case CELL_FLOOR -> Color.BLUE;
                case DOOR -> Color.YELLOW;
            };
            if (color == null) {
                return;
            }

            final int x = position.getX();
            final int y = position.getY();
            graphics2D.setPaint(color);
            graphics2D.fillRect(x*tileSize,y*tileSize,tileSize,tileSize);
        }
    }

}
