package bot.blackjack.engine;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;

public class InvalidHandForADouble extends BlackjackException {

    @NonNull
    @Getter
    private final String playerName;

    @NonNull
    @Getter
    private final ImmutableList<Hand> hands;

    public InvalidHandForADouble(@NonNull String playerName, @NonNull ImmutableList<Hand> hands) {
        super("'"+playerName+"' cannot double its bet : "+hands);
        this.playerName = playerName;
        this.hands = hands;
    }
}
