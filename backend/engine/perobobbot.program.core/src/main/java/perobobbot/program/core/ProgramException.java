package perobobbot.program.core;

import lombok.Getter;
import lombok.NonNull;

/**
 * Exception thrown when an error occurred in a program
 */
public class ProgramException extends RuntimeException {

    @NonNull
    @Getter
    private final String programName;

    public ProgramException(@NonNull String programName, @NonNull String message) {
        super("error in program '"+programName+"': "+ message);
        this.programName = programName;
    }

    public ProgramException(@NonNull String programName, @NonNull String message, Throwable cause) {
        super("error in program '"+programName+"': "+message, cause);
        this.programName = programName;
    }
}
