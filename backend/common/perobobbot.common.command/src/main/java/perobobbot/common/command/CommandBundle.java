package perobobbot.common.command;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.common.lang.SubscriptionHolder;

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
}
