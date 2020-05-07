package bot.blackjack.engine;

import lombok.NonNull;

public class InvalidHandIndex extends BlackjackException {

    @NonNull
    private final String playerName;

    private final int handIndex;

    public InvalidHandIndex(@NonNull String playerName, int handIndex) {
        super("Invalid hand index '"+handIndex+"' for player '"+playerName+"'");
        this.playerName = playerName;
        this.handIndex = handIndex;
    }
}
