package perococco.perobobbot.access;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.lang.Instants;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class BotExecutionLog {

    private final @NonNull Instants instants;

    /**
     * Execution log for each command
     */
    private final Map<String, CommandExecutionLog> executionLogs = new HashMap<>();

    @Synchronized
    public void cleanUp() {
        executionLogs.values().removeIf(CommandExecutionLog::cleanUp);
    }

    @Synchronized
    public @NonNull CommandExecutionLog getCommandExecutionLog(@NonNull String fullCommandName) {
        return executionLogs.computeIfAbsent(fullCommandName, n -> new CommandExecutionLog(instants));
    }
}
