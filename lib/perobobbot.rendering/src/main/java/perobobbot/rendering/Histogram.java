package perobobbot.rendering;

import com.google.common.collect.ImmutableList;
import lombok.*;
import perobobbot.lang.MathTool;
import perobobbot.lang.fp.UnaryOperator1;
import perobobbot.timeline.EasingType;
import perobobbot.timeline.Property;
import perobobbot.timeline.PropertyFactory;

import java.awt.*;
import java.time.Duration;
import java.util.stream.IntStream;

public class Histogram {

    public static final double DEFAULT_HIST_SIZE = 100;

    private final @NonNull ImmutableList<Property> values;

    private final @NonNull Histogram.Style style;


    public Histogram(int nbValues, @NonNull PropertyFactory propertyFactory, @NonNull Histogram.Style style) {
        this.values = IntStream.range(0, nbValues)
                               .mapToObj(i -> propertyFactory.createProperty())
                               .map(p -> p.setEasing(style.getEasingType(), style.getEasingDuration()))
                               .collect(ImmutableList.toImmutableList());
        this.style = style;
    }

    public int size() {
        return values.size();
    }

    public void setValue(int index, double value) {
        values.get(index).set(value);
    }

    public void render(@NonNull Renderer renderer, @NonNull Size size) {
        renderer.setPaint(style.getPaint());

        final var maxValue = values.stream().mapToDouble(Property::get).max().orElse(1);
        final var orientation = style.getOrientation();

        final var drawingSize = orientation.computeSize(size);

        renderer.translate(size.getWidth()*0.5,size.getHeight()*0.5);
        renderer.rotate(orientation.getAngle());
        renderer.translate(-drawingSize.getWidth()*0.5,-drawingSize.getHeight()*0.5);

        final var scaleX = drawingSize.getWidth()/(DEFAULT_HIST_SIZE *(double)values.size());
        final var scaleY = drawingSize.getHeight()/ DEFAULT_HIST_SIZE;


        renderer.scale(scaleX,scaleY);

        final double width = drawingSize.getWidth()/scaleX;
        final double height = drawingSize.getHeight()/scaleY;




        final int barWidth = MathTool.roundedToInt((width+style.getSpacing())/values.size()-style.getSpacing());

        if (barWidth <= 0) {
            return;
        }


        for (Property value : orientation.prepareList(values)) {
            double fraction = value.get()/maxValue;
            int barHeight = MathTool.roundedToInt(height*fraction);

            renderer.fillRect(0,0,barWidth,barHeight);
            renderer.translate(barWidth+style.getSpacing(),0);
        }
    }


    @RequiredArgsConstructor
    public enum Orientation {
        LEFT_TO_RIGHT(3*Math.PI/2, Size::flipHeightWithWidth, l -> l),
        RIGHT_TO_LEFT(Math.PI/2, Size::flipHeightWithWidth, ImmutableList::reverse),
        TOP_TO_BOTTOM(0 , s -> s, l -> l),
        BOTTOM_TO_TOP(Math.PI, s -> s, ImmutableList::reverse),
        ;
        @Getter
        private final double angle;
        private final UnaryOperator1<Size> sizeComputer;
        private final UnaryOperator1<ImmutableList<Property>> listPreparer;


        public @NonNull ImmutableList<Property> prepareList(@NonNull ImmutableList<Property> list) {
            return listPreparer.f(list);
        }

        public @NonNull Size computeSize(@NonNull Size size) {
            return sizeComputer.f(size);
        }
    }

    @Value
    @Builder
    public static class Style {
        @NonNull Orientation orientation;
        @NonNull EasingType easingType;
        @NonNull Duration easingDuration;
        @NonNull Paint paint;
        int spacing;

        public static @NonNull StyleBuilder builder() {
            return new StyleBuilder()
                    .orientation(Orientation.BOTTOM_TO_TOP)
                    .easingType(EasingType.EASE_OUT_SINE)
                    .easingDuration(Duration.ofSeconds(1))
                    .paint(Color.BLUE)
                    .spacing(0);
        }

    }




}
