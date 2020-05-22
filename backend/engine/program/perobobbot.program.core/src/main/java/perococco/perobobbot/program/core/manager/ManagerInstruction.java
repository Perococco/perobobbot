package perococco.perobobbot.program.core.manager;

import perobobbot.common.lang.UserRole;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.ExecutionPolicy;
import perobobbot.program.core.Instruction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import perococco.perobobbot.program.core.ManagerIdentity;

import java.time.Duration;

public abstract class ManagerInstruction implements Instruction {

    @NonNull
    @Getter(AccessLevel.PROTECTED)
    private final ManagerIdentity identity;

    @NonNull
    @Getter
    private final String name;

    @NonNull
    @Getter
    private final ExecutionPolicy executionPolicy;

    public ManagerInstruction(@NonNull ManagerIdentity identity,
                              @NonNull String name) {
        this(identity,name,ExecutionPolicy.builder().requiredRole(UserRole.TRUSTED_USER).build());
    }

    public ManagerInstruction(@NonNull ManagerIdentity identity,
                              @NonNull String name,
                              @NonNull ExecutionPolicy executionPolicy) {
        this.identity = identity;
        this.name = name;
        this.executionPolicy = executionPolicy;
    }

    protected void warnForUnknownProgram(@NonNull ExecutionContext executionContext, @NonNull String programName) {
        executionContext.print("Program '"+programName+"' is unknown.");
    }

}
