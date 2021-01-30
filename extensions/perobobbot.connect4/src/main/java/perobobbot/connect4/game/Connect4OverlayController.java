package perobobbot.connect4.game;

import lombok.NonNull;
import perobobbot.connect4.TokenType;
import perobobbot.lang.Subscription;

import java.time.Duration;

public interface Connect4OverlayController {

    void setHistogramValues(int index, int value);

    void setWinner(@NonNull Connected4 w);
    void setDraw();

    void resetForNewGame();

    @NonNull Subscription setPollStarted(@NonNull TokenType team, @NonNull Duration pollDuration);

}
