package bot.blackjack.engine;

import lombok.Getter;

public class NotCardEnoughCardInDeck extends BlackjackException {

    @Getter
    private final int requestedNumberOfCards;

    @Getter
    private final int numberOfAvailableCards;

    public NotCardEnoughCardInDeck(int requestedNumberOfCards, int numberOfAvailableCards) {
        super("Not enough cards in the deck : requested="+requestedNumberOfCards+" available="+numberOfAvailableCards);
        this.requestedNumberOfCards = requestedNumberOfCards;
        this.numberOfAvailableCards = numberOfAvailableCards;
    }
}
