package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.PerobobbotException;

import java.util.UUID;

public class UnknownUserIdentification extends PerobobbotException {

    @Getter
    private final @NonNull UUID userIdentificationId;

    public UnknownUserIdentification(UUID userIdentificationId) {
        super("Could not find any user identification with id '"+userIdentificationId+"'");

        this.userIdentificationId = userIdentificationId;
    }
}
