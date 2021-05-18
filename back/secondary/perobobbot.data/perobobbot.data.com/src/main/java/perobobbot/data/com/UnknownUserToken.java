package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

public class UnknownUserToken extends DataException {

    @Getter
    private final @NonNull UUID userTokenId;

    public UnknownUserToken(@NonNull UUID userTokenId) {
        super("Could not find user token with id '"+userTokenId+"'");
        this.userTokenId = userTokenId;
    }
}
