package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;


public class UnknownExtension extends DataException{

    @Getter
    private final @NonNull String extensionName;

    public UnknownExtension(@NonNull String extensionName) {
        super("No extension with name '"+extensionName+"' exists.");
        this.extensionName = extensionName;
    }
}
