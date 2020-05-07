package bot.blackjack.engine;

import bot.common.lang.AsyncIdentity;
import bot.common.lang.IdentityListener;
import bot.common.lang.Mutation;
import bot.common.lang.Subscription;
import bot.common.lang.fp.Function2;
import lombok.NonNull;

import java.util.concurrent.CompletionStage;

public class Game {

    @NonNull
    private final AsyncIdentity<Table> tableIdentity = AsyncIdentity.create(Table.create(8));


    @NonNull
    public CompletionStage<Table> performAction(@NonNull Mutation<Table> action) {
        return tableIdentity.mutate(action);
    }

    @NonNull
    public <A> CompletionStage<Table> performAction(@NonNull Function2<Table, A,Table> action, @NonNull A a) {
        return performAction(table -> action.apply(table,a));
    }

    @NonNull
    public Subscription addListener(@NonNull IdentityListener<Table> listener) {
        return tableIdentity.addListener(listener);
    }

    public void addWeakListener(@NonNull IdentityListener<Table> listener) {
        tableIdentity.addWeakListener(listener);
    }
}
