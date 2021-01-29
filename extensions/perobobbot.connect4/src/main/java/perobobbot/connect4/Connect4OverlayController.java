package perobobbot.connect4;

import lombok.NonNull;
import perobobbot.connect4.game.Connected4;

import java.time.Duration;

public interface Connect4OverlayController {

    void setAIStarted(@NonNull TokenType team);
    void setAIDone();

    void setHistogramValues(int[] values);

    void setWinner(@NonNull Connected4 w);
    void setDraw();

    void resetForNewGame();

    void setPollStarted(@NonNull TokenType team, @NonNull Duration pollDuration);
    void setPollDone();

}
