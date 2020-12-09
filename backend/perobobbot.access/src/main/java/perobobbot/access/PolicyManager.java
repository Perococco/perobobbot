package perobobbot.access;

import lombok.NonNull;
import perococco.perobobbot.access.PerococcoPolicyManager;

/**
 * Manager of policies (yes, this is not that informative...)
 */
public interface PolicyManager {

    @NonNull
    static PolicyManager create() {
        return new PerococcoPolicyManager();
    }

    @NonNull
    Policy createPolicy(@NonNull AccessRule accessRule);

    /**
     * Clean up any policy information (like execution time of associated
     * to user for which the cool-down has cooled down...)
     */
    void cleanUp();
}
