package perobobbot.dungeon.game.entity;

import lombok.NonNull;
import lombok.Value;

@Value
public class Movement {
    @NonNull Direction direction;
    @NonNull int amount;

}
