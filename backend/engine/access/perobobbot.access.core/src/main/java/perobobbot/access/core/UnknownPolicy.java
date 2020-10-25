package perobobbot.access.core;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.common.lang.PerobobbotException;

import java.util.UUID;

public class UnknownPolicy extends PerobobbotException {

    @Getter
    private final @NonNull UUID policyId;

    public UnknownPolicy(@NonNull UUID policyId) {
        super("Unknown policy with id '"+policyId+"'");
        this.policyId = policyId;
    }
}
