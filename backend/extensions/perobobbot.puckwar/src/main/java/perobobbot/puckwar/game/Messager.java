package perobobbot.puckwar.game;

import lombok.NonNull;

public class Constants {

    public static String formRemainingTimeText(double remainingTimeInSec) {
        return String.format("End of round in %4.1f sec.", remainingTimeInSec);
    }

    public static String formWinnerMessage(@NonNull Score score) {
        return String.format("%s wins with %.1f!",score.getUserName(),score.getScore());
    }
}
