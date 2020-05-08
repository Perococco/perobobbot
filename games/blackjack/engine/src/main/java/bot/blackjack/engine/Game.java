package bot.blackjack.engine;

import bot.common.lang.AsyncIdentity;
import bot.common.lang.IdentityListener;
import bot.common.lang.Subscription;
import lombok.Builder;
import lombok.NonNull;
import perococco.bot.blackjack.engine.DeckFactory;

public class Game {

    public static final int DEFAULT_TABLE_SIZE = 5;
    public static final int DEFAULT_DECK_SIZE = 8;


    @NonNull
    private final AsyncIdentity<Table> tableIdentity;

    @Builder
    private Game(int deckSize, int tableSize) {
        final Deck deck = DeckFactory.shuffled(deckSize);
        tableIdentity = AsyncIdentity.create(Table.create(deck, tableSize));
    }

    public @NonNull Subscription addListener(@NonNull IdentityListener<Table> listener) {
        return tableIdentity.addListener(listener);
    }

    public void addWeakListener(@NonNull IdentityListener<Table> listener) {
        tableIdentity.addWeakListener(listener);
    }

    @NonNull
    public static GameBuilder builder() {
        return new GameBuilder().deckSize(DEFAULT_DECK_SIZE)
                                .tableSize(DEFAULT_TABLE_SIZE);
    }
}
