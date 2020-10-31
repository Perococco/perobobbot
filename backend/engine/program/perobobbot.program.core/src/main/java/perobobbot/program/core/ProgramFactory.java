package perobobbot.program.core;

import lombok.NonNull;
import perobobbot.access.core.PolicyManager;
import perobobbot.service.core.Requirement;
import perobobbot.service.core.Services;

public interface ProgramFactory {

    @NonNull
    String getProgramName();

    @NonNull
    Requirement getRequirement();

    @NonNull
    Program create(@NonNull Services services, @NonNull PolicyManager policyManager);

    default boolean isAutoStart() {
        return true;
    }
}
