package perobobbot.command;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.access.Policy;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.Executor;
import perobobbot.lang.SubscriptionHolder;
import perobobbot.lang.fp.Consumer1;
import perococco.command.PeroBuilder;

/**
 * A bundle of command. This class is used
 * by spring to find commands and add them
 * to the appropriate message provider
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandBundle {

    public static CommandBundle with(@NonNull ImmutableList<Command> commands) {
        return new CommandBundle(commands);
    }

    public static CommandBundle with(@NonNull Command command) {
        return new CommandBundle(ImmutableList.of(command));
    }


    private final @NonNull ImmutableList<Command> commands;

    final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

    @Synchronized
    public void attachTo(@NonNull CommandRegistry commandRegistry) {
        subscriptionHolder.replaceWith(() -> commandRegistry.addCommands(commands));
    }

    @Synchronized
    public void detach() {
        subscriptionHolder.unsubscribe();
    }


    public static @NonNull Builder builder() { return new PeroBuilder();}


    public interface Builder {

        @NonNull
        CommandBundle.Builder add(@NonNull String name, @NonNull Policy policy, @NonNull Consumer1<? super ExecutionContext> action);

        @NonNull
        CommandBundle.Builder add(@NonNull String name, @NonNull Policy policy, @NonNull Runnable action);

        @NonNull
        CommandBundle.Builder add(@NonNull String name, @NonNull Policy policy, @NonNull Executor<? super ExecutionContext> action);

        @NonNull
        CommandBundle build();

    }
}
