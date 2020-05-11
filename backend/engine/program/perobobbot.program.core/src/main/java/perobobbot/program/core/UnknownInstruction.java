package perobobbot.program.core;

import lombok.Getter;
import lombok.NonNull;

public class UnknownInstruction extends ProgramException {

    @NonNull
    @Getter
    private final String instructionName;

    public UnknownInstruction(@NonNull String programName, @NonNull String instructionName) {
        super(programName, "unknown instruction '"+instructionName+"'");
        this.instructionName = instructionName;
    }
}
