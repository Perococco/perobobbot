package perobobbot.extension;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.common.lang.PerobobbotException;

public class UnknownExtension extends PerobobbotException {

    @Getter
    private final @NonNull String extensionName;

    public UnknownExtension(@NonNull String extensionName) {
        super("Unknown extension '"+extensionName+"'");
        this.extensionName = extensionName;
    }
}
