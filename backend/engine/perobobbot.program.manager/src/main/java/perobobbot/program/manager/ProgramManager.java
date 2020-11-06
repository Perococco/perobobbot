package perobobbot.program.manager;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.access.PolicyManager;
import perobobbot.program.core.Program;
import perobobbot.program.core.ProgramInfo;
import perobobbot.services.Services;
import perococco.perobobbot.program.manager.ProgramManagerFactory;

public interface ProgramManager extends Program {

    @NonNull
    static ProgramData<ProgramManager> create(@NonNull Services services, @NonNull PolicyManager policyManager, @NonNull ProgramRepository programRepository) {
        return ProgramManagerFactory.create(services, policyManager, programRepository);
    }

    void startProgram(@NonNull String programName);

    void stopProgram(@NonNull String programName);

    void stopAll();

    void startAll();

    @NonNull
    ImmutableSet<ProgramInfo> programInfo();

}
