package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;


public class InvalidClientType extends DataException {

    @Getter
    private final @NonNull UUID clientId;
    @Getter
    private final @NonNull Class<?> clientType;

    public InvalidClientType(@NonNull UUID clientId, @NonNull Class<?> clientType) {
        super("The client with id '%s' is not a '%s'".formatted(clientId, clientType.getSimpleName()));
        this.clientId = clientId;
        this.clientType = clientType;
    }
}
