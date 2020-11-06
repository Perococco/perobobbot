package perobobbot.access;

import lombok.NonNull;
import perococco.perobobbot.access.PerococcoPolicyManager;

public interface PolicyManager {

    @NonNull
    static PolicyManager create() {
        return new PerococcoPolicyManager();
    }

    @NonNull
    Policy createPolicy(@NonNull AccessRule accessRule);

    void cleanUp();
}
