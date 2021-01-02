package perobobbot.command;

import lombok.NonNull;
import perobobbot.lang.ExecutionContext;

public interface CommandExecutor {

    void execute(@NonNull Command command, @NonNull ExecutionContext context);
}
