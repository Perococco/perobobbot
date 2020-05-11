package perbobbot.blackjack.engine.exception;

import lombok.Getter;

public class DealToDealerFailed extends BlackjackException {

    @Getter
    private final int dealerHandValue;

    public DealToDealerFailed(int dealerHandValue) {
        super("Cannot deal another card to the dealer. Its hand is : "+dealerHandValue);
        this.dealerHandValue = dealerHandValue;
    }
}
