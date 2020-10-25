package perobobbot.program.core;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.common.lang.PerobobbotException;

public class DuplicateProgramName extends PerobobbotException {

    @Getter
    private final @NonNull String programName;

    public DuplicateProgramName(@NonNull String programName) {
        super("Duplicate program name : '"+programName+"'");
        this.programName = programName;
    }
}
