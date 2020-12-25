package perobobbot.puckwar.game;

import lombok.NonNull;
import lombok.Value;

import java.time.Duration;

@Value
public class GameOptions {

    int puckSize;

    @NonNull Duration roundDuration;
}
