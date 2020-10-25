package perobobbot.service.core;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.common.lang.PerobobbotException;

public class UnsatisfiedRequirement extends PerobobbotException {

    @NonNull
    @Getter
    private final Requirement requirement;

    public UnsatisfiedRequirement(@NonNull Requirement requirement, @NonNull Throwable cause) {
        super("Unsatisfied requirement: "+requirement,cause);
        this.requirement = requirement;
    }

    public UnsatisfiedRequirement(@NonNull Requirement requirement) {
        super("Unsatisfied requirement: "+requirement);
        this.requirement = requirement;
    }
}
