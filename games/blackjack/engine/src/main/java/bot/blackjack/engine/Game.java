package bot.blackjack.engine;

import bot.blackjack.engine.exception.InvalidBetAmount;
import bot.common.lang.*;
import bot.common.lang.fp.Consumer1;
import lombok.Builder;
import lombok.NonNull;
import perococco.bot.blackjack.engine.DeckFactory;
import perococco.bot.blackjack.engine.action.*;
import perococco.bot.blackjack.engine.action.Double;

import java.util.concurrent.CompletableFuture;
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

    public Game(Deck deck, int tableSize) {
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
    public CompletionStage<Table> withTable(@NonNull Consumer1<? super Table> consumer) {
        return tableIdentity.mutate(t -> {
            consumer.accept(t);
            return t;
        });
    }

    @NonNull
    public CompletionStage<Table> dealOneCard() {
        return tableIdentity.mutate(DealOneCard.create());
    }

    @NonNull
    public CompletionStage<Table> dealAllHands() {
        return dealOneCard()
                .thenCompose(t -> {
                    if (t.state() == TableState.PLAYER_PHASE) {
                        return CompletableFuture.completedFuture(t);
                    } else {
                        return dealAllHands();
                    }
                });
    }

    @NonNull
    public CompletionStage<Table> addPlayer(@NonNull String playerName, int betAmount) {
        if (betAmount<=0) {
            throw new InvalidBetAmount(playerName, betAmount);
        }
        return tableIdentity.mutate(AddPlayer.with(playerName,betAmount));
    }

    @NonNull
    public CompletionStage<Table> stand(String playerName) {
        return tableIdentity.mutate(Stand.with(playerName));
    }

    @NonNull
    public CompletionStage<Table> hit(@NonNull String playerName) {
        return tableIdentity.mutate(HitPlayer.with(playerName));
    }

    @NonNull
    public CompletionStage<Table> split(String playerName) {
        return tableIdentity.mutate(Split.with(playerName));
    }

    @NonNull
    public CompletionStage<Table> doubleHand(String playerName) {
        return tableIdentity.mutate(Double.with(playerName));
    }
}
