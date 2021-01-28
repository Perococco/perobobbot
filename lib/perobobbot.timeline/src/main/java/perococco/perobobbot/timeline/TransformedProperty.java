package perococco.perobobbot.timeline;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.timeline.ReadOnlyProperty;

@RequiredArgsConstructor
public class TransformedProperty implements ReadOnlyProperty {

    private final ReadOnlyProperty reference;
    private final double factor;
    private final double offset;

    @Override
    public double get() {
        return offset+factor*reference.get();
    }

    @Override
    public @NonNull ReadOnlyProperty withTransformation(int factor, int offset) {
        var newFactor = factor*this.factor;
        var newOffset = factor*this.offset+offset;
        return new TransformedProperty(reference,newFactor,newOffset);
    }
}
