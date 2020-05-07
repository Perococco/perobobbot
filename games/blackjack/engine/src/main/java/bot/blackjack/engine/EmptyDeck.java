package bot.blackjack.engine;

public class EmptyDeck extends BlackjackException {

    public EmptyDeck() {
        super("Cannot pick a card. The deck is empty");
    }
}
