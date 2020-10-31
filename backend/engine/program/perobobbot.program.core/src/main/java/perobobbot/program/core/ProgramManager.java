package perobobbot.program.core;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.access.core.PolicyManager;
import perobobbot.service.core.Services;
import perococco.perobobbot.program.core.ProgramManagerFactory;

public interface ProgramManager {

    @NonNull
    static ProgramManager create(@NonNull Services services, @NonNull PolicyManager policyManager) {
        return ProgramManagerFactory.create(services, policyManager);
    }

    void enable();

    void disable();

    void startProgram(@NonNull String programName);

    void stopProgram(@NonNull String programName);

    void stopAll();

    void startAll();

    @NonNull
    ImmutableSet<ProgramInfo> programInfo();

}
