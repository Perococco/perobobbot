package perobobbot.puckwar.game;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TargetDrawer {

    public static void draw(@NonNull Graphics2D graphics2D, int size) {
        new TargetDrawer(graphics2D,size).draw();
    }

    public static final int DRAWING_AREA_SIZE = 1000;


    public static final List<Circle> CIRCLES = List.of(
            Circle.of(980,18,Color.RED),
            Circle.of(400,10,Color.RED),
            Circle.of(100,5,Color.RED)
                                                       );


    private final @NonNull Graphics2D graphics2D;
    private final int size;

    public void draw() {
        this.setupTransformation();
        this.setupRenderingQuality();
        this.drawCircles();
        this.drawTargetLines();
    }

    private void setupTransformation() {
        final var scale = 2.*DRAWING_AREA_SIZE/(double)size;
        this.graphics2D.scale(1./scale,1./scale);
        this.graphics2D.translate(DRAWING_AREA_SIZE,DRAWING_AREA_SIZE);
    }

    private void setupRenderingQuality() {
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private void drawCircles() {
        CIRCLES.forEach(c -> c.draw(graphics2D));
    }

    private void drawTargetLines() {
        CIRCLES.stream()
               .max(Comparator.comparingInt(Circle::getRadius))
               .map(Circle::getRadius)
               .ifPresent(r ->  {
                   graphics2D.setPaint(Color.RED);
                   graphics2D.drawLine(-r,0,r,0);
                   graphics2D.drawLine(0,-r,0,r);
               });
    }

    @Value
    private static class Circle {

        public static Circle of(int radius, int thickness, @NonNull Color color) {
            return new Circle(radius,thickness,color);
        }

        int radius;

        int thickness;

        Color color;

        public void draw(@NonNull Graphics2D graphics2D) {
            graphics2D.setPaint(color);
            graphics2D.setStroke(new BasicStroke(thickness));
            graphics2D.drawOval(-radius,-radius,radius*2,radius*2);
        }
    }
}
