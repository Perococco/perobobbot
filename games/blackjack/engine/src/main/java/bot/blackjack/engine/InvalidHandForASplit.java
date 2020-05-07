package bot.blackjack.engine;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;

public class InvalidHandForASplit extends BlackjackException {

    @NonNull
    @Getter
    private final String playerName;

    @NonNull
    @Getter
    private final ImmutableList<Hand> hands;

    public InvalidHandForASplit(@NonNull String playerName, @NonNull ImmutableList<Hand> hands) {
        super("'"+playerName+"' cannot split its hands : "+hands);
        this.playerName = playerName;
        this.hands = hands;
    }
}
