package perbobbot.blackjack.engine.exception;

public class EmptyDeck extends BlackjackException {

    public EmptyDeck() {
        super("Cannot pick a card. The deck is empty");
    }
}
