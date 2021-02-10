package perococco.perobobbot.access;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.access.ExecutionManager;
import perobobbot.access.Launcher;
import perobobbot.lang.ChatUser;
import perobobbot.lang.Instants;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class PerococcoExecutionManager implements ExecutionManager {

    private final Map<UUID, BotExecutionLog> executionLogs = new HashMap<>();

    private final @NonNull Instants instants;

    @Synchronized
    @Override
    public void cleanUp() {
        executionLogs.values().forEach(BotExecutionLog::cleanUp);
    }

    @Synchronized
    private @NonNull CommandExecutionLog getCommandExecutionLog(@NonNull UUID botId, @NonNull String fullCommandName) {
        return executionLogs.computeIfAbsent(botId, id -> new BotExecutionLog(instants))
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
