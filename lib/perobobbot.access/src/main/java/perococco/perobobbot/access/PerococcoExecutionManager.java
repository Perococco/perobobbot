package perococco.perobobbot.access;

import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.access.ExecutionManager;
import perobobbot.access.Launcher;
import perobobbot.lang.ChatUser;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PerococcoExecutionManager implements ExecutionManager {

    private final Map<UUID, BotExecutionLog> executionLogs = new HashMap<>();

    @Synchronized
    @Override
    public void cleanUp() {
        executionLogs.values().forEach(BotExecutionLog::cleanUp);
    }

    @Synchronized
    private @NonNull CommandExecutionLog getCommandExecutionLog(@NonNull UUID botId, @NonNull String fullCommandName) {
        return executionLogs.computeIfAbsent(botId, id -> new BotExecutionLog())
                            .getCommandExecutionLog(fullCommandName);
    }

    @Override
    public @NonNull Launcher getLauncher(@NonNull UUID botId,
                                @NonNull String fullCommandName,
                                @NonNull ChatUser chatUser) {
        final var executionLog = getCommandExecutionLog(botId,fullCommandName);
        return executionLog.createLauncher(chatUser);
    }
}
