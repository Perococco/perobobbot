package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

public class UnknownClientId extends DataException {

    @NonNull
    @Getter
    private final UUID clientId;

    public UnknownClientId(@NonNull UUID clientId) {
        super("Could not find any client with clientId='"+clientId+"'");
        this.clientId = clientId;
    }
}
