package bot.blackjack.engine;

import bot.common.lang.*;
import bot.common.lang.fp.Consumer1;
import lombok.Builder;
import lombok.NonNull;
import perococco.bot.blackjack.engine.DeckFactory;

import java.util.concurrent.CompletionStage;

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

    @NonNull
    public Table getTable() {
        return tableIdentity.getRootState();
    }

    @NonNull
    public CompletionStage<Game> withTable(@NonNull Consumer1<? super Table> consumer) {
        return mutate(t -> {
            consumer.accept(t);
            return t;
        });
    }

    @NonNull
    public CompletionStage<Game> mutate(@NonNull Mutation<Table> mutation) {
        return tableIdentity.mutate(mutation).thenApply(t -> this);
    }
}
