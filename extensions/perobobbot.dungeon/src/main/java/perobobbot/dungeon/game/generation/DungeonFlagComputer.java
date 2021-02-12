package perobobbot.dungeon.game.generation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.dungeon.game.DungeonMap;
import perobobbot.dungeon.game.ExtraFlag;
import perobobbot.dungeon.game.entity.Direction;
import perococco.jdgen.api.CellType;
import perococco.jdgen.api.Position;

import java.util.function.Predicate;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class DungeonFlagComputer {

    public static void compute(@NonNull DungeonMap map) {
        new DungeonFlagComputer(map).compute();
    }

    private final @NonNull DungeonMap map;

    private void compute() {
        map.allMapPositions()
           .forEach(this::computeWallFlag);
    }

    private void computeWallFlag(@NonNull Position position) {
        final var cellType = map.getCellTypeAt(position);
        final Predicate<CellType> tester;

        if (cellType == CellType.EMPTY) {
            tester = this::isWall;
        } else {
            tester = this::isFloor;
        }

        final int flag;
        {
            flag = computeFlag(Direction.allDirections(), position, tester);
        }
        final ExtraFlag extraFlag;
        {
            final var value = computeFlag(Stream.of(Direction.SOUTH, Direction.SOUTH_EAST),
                                          Direction.SOUTH.moveByOne(position), this::isFloor);
            extraFlag = ExtraFlag.getByValue(value);
        }

        map.getCellAt(position).setFlag(flag);
        map.getCellAt(position).setExtraFlag(extraFlag);
    }

    private @NonNull Predicate<Direction> createDirectionPredicate(@NonNull Position position, @NonNull Predicate<CellType> cellTypePredicate) {
        return direction -> {
            final var type = map.getCellTypeAt(direction.moveByOne(position));
            return cellTypePredicate.test(type);
        };
    }

    private @NonNull int computeFlag(@NonNull Stream<Direction> directions, @NonNull Position position, @NonNull Predicate<CellType> tester) {
        final Predicate<Direction> predicate = createDirectionPredicate(position, tester);
        return directions
                .filter(predicate)
                .mapToInt(Direction::getMask)
                .reduce(0, (i1, i2) -> i1 | i2);

    }

    private boolean isFloor(@NonNull CellType cellType) {
        return cellType.isFloor() || cellType == CellType.DOOR;
    }

    private boolean isWall(@NonNull CellType cellType) {
        return cellType == CellType.WALL;
    }
}
