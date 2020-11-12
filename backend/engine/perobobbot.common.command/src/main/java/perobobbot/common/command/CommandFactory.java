package perobobbot.common.command;

import lombok.NonNull;
import perobobbot.access.Policy;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.Executor;
import perobobbot.common.lang.fp.Consumer1;

public interface CommandFactory {

    @NonNull
    CommandFactory add(@NonNull String name, @NonNull Policy policy, @NonNull Consumer1<? super ExecutionContext> action);

    @NonNull
    CommandFactory add(@NonNull String name, @NonNull Policy policy, @NonNull Runnable action);

    @NonNull
    CommandFactory add(@NonNull String name, @NonNull Policy policy, @NonNull Executor<? super ExecutionContext> action);

    @NonNull
    CommandBundle build();

}
