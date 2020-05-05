package perococco.bot.program.manager;

import bot.common.lang.UserRole;
import bot.program.ExecutionContext;
import bot.program.ExecutionPolicy;
import bot.program.Instruction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import perococco.bot.program.ManagerIdentity;

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
        this(identity,name,new ExecutionPolicy(UserRole.TRUSTED_USER, Duration.ZERO,Duration.ZERO));
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
