package perobobbot.data.com;

import lombok.NonNull;


public class UnknownExtension extends DataException{

    private final @NonNull String extensionName;

    public UnknownExtension(@NonNull String extensionName) {
        super("No extension with name '"+extensionName+"' exists.");
        this.extensionName = extensionName;
    }
}
