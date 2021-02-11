package perobobbot.dungeon.game.generation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.dungeon.game.DungeonMap;
import perobobbot.dungeon.game.entity.Direction;
import perococco.jdgen.api.CellType;
import perococco.jdgen.api.Position;

import java.util.function.Predicate;

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
            tester = c -> c == CellType.WALL;
        } else {
            tester = c -> c.isFloor() || c == CellType.DOOR;
        }
        final Predicate<Direction> predicate = createDirectionPredicate(position,tester);

        final var flag = Direction.allDirections()
                                  .stream()
                                  .filter(predicate)
                                  .mapToInt(Direction::getMask)
                                  .reduce(0, (i1, i2) -> i1 | i2);

        map.getCellAt(position).setFlag(flag);
    }

    private @NonNull Predicate<Direction> createDirectionPredicate(@NonNull Position position, @NonNull Predicate<CellType> cellTypePredicate) {
        return direction -> {
            final var type = map.getCellTypeAt(direction.moveByOne(position));
            return cellTypePredicate.test(type);
        };
    }
}
