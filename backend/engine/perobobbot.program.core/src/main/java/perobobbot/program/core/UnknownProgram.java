package perobobbot.program.core;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.common.lang.PerobobbotException;

public class UnknownProgram extends PerobobbotException {

    @Getter
    private final @NonNull String programName;

    public UnknownProgram(@NonNull String programName) {
        super("Unknown program '"+programName+"'");
        this.programName = programName;
    }
}
