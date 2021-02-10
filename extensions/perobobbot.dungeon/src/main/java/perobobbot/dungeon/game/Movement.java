package perobobbot.dungeon.game;

import lombok.NonNull;
import lombok.Value;

@Value
public class Movement {
    @NonNull Direction direction;
    @NonNull int amount;

}
