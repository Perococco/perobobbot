package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

public class UnknownClient extends DataException {

    @NonNull
    @Getter
    private final UUID clientId;

    public UnknownClient(@NonNull UUID clientId) {
        super("Could not find any user with clientId='"+clientId+"'");
        this.clientId = clientId;
    }
}
