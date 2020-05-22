package perbobbot.blackjack.engine.exception;

import lombok.Getter;
import lombok.NonNull;
import perbobbot.blackjack.engine.TableState;

public class InvalidTableState extends BlackjackException {

    @NonNull
    @Getter
    private final TableState expectedState;

    @NonNull
    @Getter
    private final TableState actualState;

    public InvalidTableState(@NonNull TableState expectedState, @NonNull TableState actualState) {
        super("Invalid table state : expected="+expectedState+" actual="+actualState);
        this.expectedState = expectedState;
        this.actualState = actualState;
    }
}
