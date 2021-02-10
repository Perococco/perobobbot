package perobobbot.dungeon.game;

import lombok.NonNull;
import perococco.jdgen.api.Map;
import perococco.jdgen.api.Position;

public class DungeonGame {

    private @NonNull Position playerPosition;

    private @NonNull Map<DungeonCell> map;

//    public void movePlayer(@NonNull Movement movement) {
//        //TODO
//        final var newPosition = MovementExecutor.execute(map,playerPosition,movement);
//    }



}
