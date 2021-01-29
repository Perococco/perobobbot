package perobobbot.rendering.histogram;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.lang.MathTool;
import perobobbot.rendering.Renderer;
import perobobbot.rendering.RenderingTools;
import perobobbot.rendering.Size;
import perobobbot.timeline.Property;
import perobobbot.timeline.PropertyFactory;

public class Histogram {

    private final @NonNull ImmutableList<Property> values;

    private @NonNull HistogramStyle style;

    public Histogram(int nbValues, @NonNull PropertyFactory propertyFactory, @NonNull HistogramStyle style) {
        final var builder = ImmutableList.<Property>builder();
        for (int i = 0; i < nbValues; i++) {
            builder.add(propertyFactory.createProperty()
                                       .setEasing(style.getEasingType(),style.getEasingDuration()));
        }
        this.values = builder.build();
        this.style = style;
    }

    public Histogram(int nbValues, @NonNull PropertyFactory propertyFactory) {
        this(nbValues, propertyFactory, HistogramStyle.builder().build());
    }

    public void clear() {
        values.forEach(v -> v.forceSet(0));
    }

    public int size() {
        return values.size();
    }

    public void setValue(int index, double value) {
        values.get(index).set(value);
    }

    public void forceSetValue(int index, double value) {
        values.get(index).forceSet(value);
    }

    public void render(@NonNull Renderer renderer, @NonNull Size size) {
        renderer.withPrivateContext(r -> {
            final var maxValue = values.stream().mapToDouble(Property::get).max().orElse(1);
            final var orientation = style.getOrientation();

            final var drawingSize = orientation.computeSize(size);

            r.translate(size.getWidth() * 0.5, size.getHeight() * 0.5);
            r.rotate(orientation.getAngle());
            r.translate(-drawingSize.getWidth() * 0.5, -drawingSize.getHeight() * 0.5);

            final double width = drawingSize.getWidth();
            final double height = drawingSize.getHeight();


            final double barWidth = RenderingTools.computeCellSize(width, values.size(), style.getMargin(), style.getSpacing());

            if (barWidth <= 0) {
                return;
            }

            r.translate(style.getMargin(), 0);
            for (Property value : orientation.prepareList(values)) {
                double fraction = value.get() / maxValue;
                int barHeight = MathTool.roundedToInt(height * fraction);

                style.drawBar(r, barWidth, barHeight, 100 * fraction);
                r.translate(barWidth + style.getSpacing(), 0);
            }
        });
    }


    public void setStyle(@NonNull HistogramStyle style) {
        this.style = style;
    }
}
