package perobobbot.access;

import lombok.NonNull;
import perobobbot.lang.ChatUser;
import perobobbot.lang.Instants;
import perococco.perobobbot.access.PerococcoExecutionManager;

import java.util.UUID;

/**
 * Manage the execution of the command
 */
public interface ExecutionManager {

    @NonNull
    static ExecutionManager create(@NonNull Instants instants) {
        return new PerococcoExecutionManager(instants);
    }

    /**
     * Clean up any policy information (like execution time of associated
     * to user for which the cool-down has cooled down...)
     */
    void cleanUp();

    @NonNull Launcher getLauncher(@NonNull UUID botId,
                                  @NonNull String fullCommandName,
                                  @NonNull ChatUser chatUser);
}
