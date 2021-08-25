package perobobbot.data.com;

import lombok.NonNull;
import perobobbot.lang.PerobobbotException;
import perobobbot.security.com.IdentificationMode;

public class InvalidIdentificationMode extends PerobobbotException {

    public InvalidIdentificationMode(@NonNull IdentificationMode expected, @NonNull IdentificationMode actual) {
        super("Invalid identification mode : expected="+expected+ "  actual="+actual);
    }
}
