package perobobbot.dungeon.game;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.fp.Function2;

@RequiredArgsConstructor
public enum Direction {
    NORTH(Position::moveNorth),
    SOUTH(Position::moveSouth),
    WEST(Position::moveWest),
    EAST(Position::moveEast),
    NORTH_WEST(Position::moveNorthWest),
    NORTH_EAST(Position::moveNorthEast),
    SOUTH_WEST(Position::moveSouthWest),
    SOUTH_EAST(Position::moveSouthEast),
    ;

    private final @NonNull Function2<? super Position, ? super Integer, ? extends Position> mover;

    public @NonNull Position computeDestination(@NonNull Position origin, int amount) {
        return mover.apply(origin,amount);
    }

    public @NonNull Position moveByOne(@NonNull Position origin) {
        return mover.apply(origin,1);
    }

    public @NonNull Movement createMovement(int amount) {
        return new Movement(this,amount);
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
