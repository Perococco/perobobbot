package perobobbot.timeline;

import lombok.NonNull;

public interface ReadOnlyProperty {

    double get();

    @NonNull ReadOnlyProperty withTransformation(int factor,int offset);

    default @NonNull ReadOnlyProperty withOffset(int offset) {
        return withTransformation(1,offset);
    }

    default @NonNull ReadOnlyProperty withScale(int factor) {
        return withTransformation(factor,0);
    }


}
