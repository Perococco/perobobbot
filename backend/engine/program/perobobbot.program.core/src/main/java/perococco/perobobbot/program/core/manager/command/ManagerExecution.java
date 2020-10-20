package perococco.perobobbot.program.core.manager.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import perobobbot.common.lang.Role;
import perobobbot.program.core.Execution;
import perobobbot.program.core.ExecutionPolicy;
import perobobbot.program.core.ProgramAction;

public abstract class ManagerExecution implements Execution {

    @NonNull
    @Getter(AccessLevel.PROTECTED)
    private final ProgramAction programAction;

    @NonNull
    @Getter
    private final String name;

    @NonNull
    @Getter
    private final ExecutionPolicy executionPolicy;

    public ManagerExecution(@NonNull String name,
                            @NonNull ProgramAction programAction) {
        this(name, programAction, ExecutionPolicy.builder()
                                                 .requiredRole(Role.TRUSTED_USER)
                                                 .build());
    }

    public ManagerExecution(@NonNull String name,
                            @NonNull ProgramAction programAction,
                            @NonNull ExecutionPolicy executionPolicy) {
        this.programAction = programAction;
        this.name = name;
        this.executionPolicy = executionPolicy;
    }

}
