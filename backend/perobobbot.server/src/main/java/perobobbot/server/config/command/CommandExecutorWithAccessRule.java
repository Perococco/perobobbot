package perobobbot.server.config.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import perobobbot.access.AccessRule;
import perobobbot.access.ExecutionManager;
import perobobbot.command.Command;
import perobobbot.command.CommandExecutor;
import perobobbot.data.service.ExtensionService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.lang.ExecutionContext;

@RequiredArgsConstructor
@Component
@Qualifier("with-access-rule")
public class CommandExecutorWithAccessRule implements CommandExecutor {

    private final @NonNull CommandExecutor delegate;

    private final @NonNull @UnsecuredService ExtensionService extensionService;

    private final @NonNull ExecutionManager executionManager;

    @Override
    public void execute(@NonNull Command command, @NonNull ExecutionContext context) {
        final var accessRule = this.retrieveAccessRule(command);

        final var launcher = executionManager.getLauncher(
                context.getBotId(),
                command.getCommandFullName(),
                context.getMessageOwner());

        final Runnable action = () -> delegate.execute(command, context);
        launcher.launch(action, accessRule, context.getReceptionTime());
    }

    private AccessRule retrieveAccessRule(@NonNull Command command) {
        //TODO retrieve access rule for specific bot
        return command.getDefaultAccessRule();
    }
}
