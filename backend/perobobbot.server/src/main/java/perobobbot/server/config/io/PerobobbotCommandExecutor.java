package perobobbot.server.config.io;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.command.Command;
import perobobbot.command.CommandExecutor;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.Todo;

@Component
public class PerobobbotCommandExecutor implements CommandExecutor {

    @Override
    public void execute(@NonNull Command command, @NonNull ExecutionContext context) {
        Todo.TODO();
    }
}
