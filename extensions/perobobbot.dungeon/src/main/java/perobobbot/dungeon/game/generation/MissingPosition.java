package perobobbot.dungeon.game.generation;

import lombok.NonNull;
import lombok.Value;
import perobobbot.dungeon.game.ExtraFlag;
import perococco.jdgen.api.Position;

@Value
public class MissingPosition {

    @NonNull String classInfo;
    @NonNull Position position;
    @NonNull String flags;

}
