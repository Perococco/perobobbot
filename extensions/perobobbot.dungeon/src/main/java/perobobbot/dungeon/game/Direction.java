package perobobbot.dungeon.game;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.jdgen.api.Position;

import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public enum Direction {
    NORTH(0, -1),
    SOUTH(0, +1),
    WEST(-1, 0),
    EAST(+1, 0),
    NORTH_WEST(-1, -1),
    NORTH_EAST(+1, -1),
    SOUTH_WEST(-1, +1),
    SOUTH_EAST(+1, +1),
    ;

    private final @NonNull UnaryOperator<Position> mover;

    Direction(int dx, int dy) {
        this.mover = p -> p.translate(dx,dy);
    }

    public @NonNull Position moveByOne(@NonNull Position origin) {
        return mover.apply(origin);
    }

    public @NonNull Movement createMovement(int amount) {
        return new Movement(this, amount);
    }

    public static @NonNull ImmutableList<Direction> allDirections() {
        return Holder.DIRECTIONS;
    }

    private static class Holder {

        private final static ImmutableList<Direction> DIRECTIONS = ImmutableList.of(
                Direction.NORTH_WEST,
                Direction.NORTH,
                Direction.WEST,
                Direction.NORTH_EAST,
                Direction.SOUTH_EAST,
                Direction.EAST,
                Direction.SOUTH,
                Direction.SOUTH_WEST
        );
    }

}
