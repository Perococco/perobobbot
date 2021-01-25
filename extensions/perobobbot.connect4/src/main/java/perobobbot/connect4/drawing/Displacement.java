package perobobbot.connect4.drawing;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;

import java.util.stream.Stream;

@Value
public class Displacement {

    public static @NonNull Displacement of(int deltaColumn, int deltaRow) {
        return new Displacement(deltaColumn,deltaRow);
    }

    int deltaColumn;
    int deltaRow;

    public @NonNull Displacement negate() {
        return new Displacement(-deltaColumn, -deltaRow);
    }

    public static @NonNull Stream<Displacement> halfCircleDisplacements() {
        return Holder.HALF_CIRCLE.stream();
    }

    public static class Holder {

        private final static ImmutableList<Displacement> HALF_CIRCLE = ImmutableList.of(
                    of(0,1),
                    of(1,0),
                    of(1,1),
                    of(1,-1)
                );
    }
}
