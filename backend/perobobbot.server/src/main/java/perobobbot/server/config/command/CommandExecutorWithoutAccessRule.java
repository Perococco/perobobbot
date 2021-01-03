package perobobbot.server.config.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import perobobbot.command.Command;
import perobobbot.command.CommandExecutor;
import perobobbot.command.MessageErrorResolver;
import perobobbot.data.service.EventService;
import perobobbot.data.service.ExtensionService;
import perobobbot.lang.ExecutionContext;

@RequiredArgsConstructor
@Log4j2
@Component
@Qualifier("without-access-rule")
public class CommandExecutorWithoutAccessRule implements CommandExecutor {

    /**
     * used to convert exception that occurs when executing a command, to error message
     */
    private final @NonNull MessageErrorResolver messageErrorResolver;

    private final @NonNull
    @EventService
    ExtensionService extensionService;

    @Override
    public void execute(@NonNull Command command, @NonNull ExecutionContext context) {
        final boolean enabled;
        if (!context.getPlatform().isLocal()) {
            enabled = extensionService.isExtensionEnabled(context.getBotId(), command.getExtensionName());
        } else {
            enabled = true;
        }
        if (!enabled) {
            return;
        }
        new Execution(command, context).execute();
    }

    @RequiredArgsConstructor
    private class Execution {

        private final @NonNull Command command;
        private final @NonNull ExecutionContext executionContext;

        private Throwable error;

        private void execute() {
            this.launchCommand();
            if (this.anErrorOccurred()) {
                this.displayErrorMessage();
            }
        }

        private void launchCommand() {
            this.error = command.tryToExecute(executionContext)
                                .failure()
                                .orElse(null);
        }

        private boolean anErrorOccurred() {
            return this.error != null;
        }

        private void displayErrorMessage() {
            final var message = messageErrorResolver.resolve(error).orElse(error.getMessage());
            LOG.warn("Error while executing command '{}' : {}", command.getCommandFullName(), message, error);
        }

    }
}
