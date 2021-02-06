package perobobbot.rendering.histogram;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.fp.UnaryOperator1;
import perobobbot.rendering.Size;
import perobobbot.timeline.Property;

@RequiredArgsConstructor
public enum Orientation {
    LEFT_TO_RIGHT(3 * Math.PI / 2, Size::flipHeightWithWidth, l -> l),
    RIGHT_TO_LEFT(Math.PI / 2, Size::flipHeightWithWidth, ImmutableList::reverse),
    TOP_TO_BOTTOM(0, s -> s, l -> l),
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
