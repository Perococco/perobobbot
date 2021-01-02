package perobobbot.server.config.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import perobobbot.command.Command;
import perobobbot.command.CommandExecutor;
import perobobbot.command.MessageErrorResolver;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.Todo;

@Component
@RequiredArgsConstructor
public class PerobobbotCommandExecutor implements CommandExecutor {

    /**
     * used to convert exception that occurs when executing a command, to error message
     */
    private final @NonNull MessageErrorResolver messageErrorResolver;

    @Override
    public void execute(@NonNull Command command, @NonNull ExecutionContext context) {
        Todo.TODO();
    }
}
