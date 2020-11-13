package perobobbot.puckwar.game;

import lombok.NonNull;

public class Messager {

    public static @NonNull String formRemainingTimeMessage(double remainingTimeInSec) {
        return String.format("End of round in %4.1f sec.", remainingTimeInSec);
    }

    public static @NonNull String formWinnerMessage(@NonNull Score score) {
        return String.format("%s wins with %.1f!",score.getUserName(),score.getScore());
    }

    public static @NonNull String getHighScoreTableTitle() {
        return "Score";
    }
}
