package perobobbot.dungeon.game.entity;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.fp.Function2;
import perococco.jdgen.api.Position;

import java.util.function.ToIntBiFunction;
import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public enum Direction {
    NORTH_WEST(-1, -1,8),
    NORTH(0, -1,7),
    NORTH_EAST(+1, -1,6),
    WEST(-1, 0,5),
    EAST(+1, 0,3),
    SOUTH_WEST(-1, +1,2),
    SOUTH(0, +1,1),
    SOUTH_EAST(+1, +1,0),
    ;

    private final @NonNull Function2<Position,Integer,Position> mover;

    @Getter
    private final int mask;

    Direction(int dx, int dy,int bit) {
        this.mover = (p,a) -> p.translate(dx*a,dy*a);
        this.mask = 1<<bit;
    }

    public @NonNull Position moveByOne(@NonNull Position origin) {
        return mover.apply(origin,1);
    }

    public @NonNull Position moveBy(@NonNull Position origin, int amount) {
        return mover.apply(origin,amount);
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
