package perobobbot.dungeon.game;

import lombok.NonNull;
import lombok.Value;

@Value
public class Movement {
    @NonNull Direction direction;
    @NonNull int amount;

    public @NonNull Position computeDestination(@NonNull Position origin) {
        return direction.computeDestination(origin,amount);
    }
}
