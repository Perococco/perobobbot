package perobobbot.dungeon.game;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.jdgen.api.CellType;
import perococco.jdgen.api.Map;

@RequiredArgsConstructor
public class MovementExecutor {

    public static @NonNull Position execute(
             @NonNull Map<DungeonCell> map,
             @NonNull Position playerPosition,
             @NonNull Movement movementToExecute
    ) {
        return new MovementExecutor(map, playerPosition, movementToExecute).execute();
    }

    private final @NonNull Map<DungeonCell> map;
    private final @NonNull Position playerPosition;
    private final @NonNull Movement movementToExecute;

    private Direction direction;
    private int leftAmount;

    private Position currentPosition;

    private Position nextPosition;

    private @NonNull Position execute() {
        this.initializeDirectionAndAmount();
        this.initializeCurrentPosition();

        do {
            if (!tryToPerformOneStep()) {
                break;
            }
        } while (leftAmount>0);


        return currentPosition;
    }

    private void initializeDirectionAndAmount() {
        leftAmount = movementToExecute.getAmount();
        direction = movementToExecute.getDirection();
    }

    private void initializeCurrentPosition() {
        currentPosition = playerPosition;
    }

    /**
     * @return true is the step could be performed
     */
    private boolean tryToPerformOneStep() {
        if (leftAmount<=0) {
            return false;
        }
        leftAmount--;
        nextPosition = direction.moveByOne(currentPosition);
        if (destinationIsFloor() || destinationIsOpenDoor()) {
            currentPosition = nextPosition;
            return true;
        }
        return false;

    }

    private boolean destinationIsFloor() {
        return map.getCellAt(nextPosition).isFloor();
    }

    private boolean destinationIsOpenDoor() {
        return map.getCellAt(nextPosition).getType() == CellType.DOOR;
    }
}
