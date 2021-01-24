package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

public class UnknownCredential extends DataException {

    @Getter
    private final @NonNull UUID id;

    public UnknownCredential(@NonNull UUID id) {
        super("No credential with '"+id+"' could be found.");
        this.id = id;
    }
}
