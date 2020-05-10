package bot.blackjack.engine.exception;

import lombok.Getter;
import lombok.NonNull;

public class InvalidBetAmount extends BlackjackException {

    @NonNull
    @Getter
    private final String playerName;

    @Getter
    private final int betAmount;

    public InvalidBetAmount(@NonNull String playerName, int betAmount) {
        super("'"+playerName+"' the bet amount is invalid : "+betAmount);
        this.playerName = playerName;
        this.betAmount = betAmount;
    }
}
