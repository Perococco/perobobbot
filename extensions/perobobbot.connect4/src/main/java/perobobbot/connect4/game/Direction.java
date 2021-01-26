package perobobbot.connect4.game;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;

import java.util.stream.Stream;

@Value
public class Direction {

    public static @NonNull Direction of(int deltaColumn, int deltaRow) {
        return new Direction(deltaColumn, deltaRow);
    }

    int deltaColumn;
    int deltaRow;

    public @NonNull Direction reverse() {
        return new Direction(-deltaColumn, -deltaRow);
    }

    public static @NonNull Stream<Direction> halfCircleDisplacements() {
        return Holder.HALF_CIRCLE.stream();
    }

    public static class Holder {

        private final static ImmutableList<Direction> HALF_CIRCLE = ImmutableList.of(
                    of(0,1),
                    of(1,0),
                    of(1,1),
                    of(1,-1)
                );
    }
}
