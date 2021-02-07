package perobobbot.connect4.game;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.connect4.Team;
import perobobbot.lang.Subscription;

import java.time.Duration;

public interface Connect4OverlayController {

    void setHistogramValues(int index, int value);

    void setWinner(@NonNull WinningPosition w);
    void setDraw();

    void resetForNewGame();

    @NonNull Subscription onPollStarted(@NonNull Team team, @NonNull ImmutableList<String> options);

    @NonNull void onPollTimerStarted(@NonNull Team team, @NonNull Duration pollDuration);

}
