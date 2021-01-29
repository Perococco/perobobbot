package perobobbot.rendering.histogram;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.lang.MathTool;
import perobobbot.rendering.Renderer;
import perobobbot.rendering.RenderingTools;
import perobobbot.rendering.Size;
import perobobbot.timeline.Property;
import perobobbot.timeline.PropertyFactory;

import java.util.stream.IntStream;

public class Histogram {

    private final @NonNull ImmutableList<Property> values;

    private final @NonNull HistogramStyle style;

    public Histogram(int nbValues, @NonNull PropertyFactory propertyFactory, @NonNull HistogramStyle style) {
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

        final var maxValue = values.stream().mapToDouble(Property::get).max().orElse(1);
        final var orientation = style.getOrientation();

        final var drawingSize = orientation.computeSize(size);

        renderer.translate(size.getWidth()*0.5,size.getHeight()*0.5);
        renderer.rotate(orientation.getAngle());
        renderer.translate(-drawingSize.getWidth()*0.5,-drawingSize.getHeight()*0.5);

        final double width = drawingSize.getWidth();
        final double height = drawingSize.getHeight();


        final double barWidth = RenderingTools.computeCellSize(width,values.size(),style.getMargin(),style.getSpacing());

        if (barWidth <= 0) {
            return;
        }

        renderer.translate(style.getMargin(),0);
        for (Property value : orientation.prepareList(values)) {
            double fraction = value.get()/maxValue;
            int barHeight = MathTool.roundedToInt(height*fraction);

            style.drawBar(renderer,barWidth,barHeight,100*fraction);
            renderer.translate(barWidth+style.getSpacing(),0);
        }
    }


}
