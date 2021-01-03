package perobobbot.access;

import lombok.NonNull;
import perobobbot.lang.ChatUser;
import perococco.perobobbot.access.PerococcoExecutionManager;

import java.util.UUID;

/**
 * Manager of policies (yes, this is not that informative...)
 */
public interface ExecutionManager {

    @NonNull
    static ExecutionManager create() {
        return new PerococcoExecutionManager();
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
