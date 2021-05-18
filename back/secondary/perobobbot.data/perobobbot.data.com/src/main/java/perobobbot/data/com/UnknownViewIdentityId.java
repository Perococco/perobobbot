package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

public class UnknownViewIdentityId extends DataException {

    @NonNull
    @Getter
    private final UUID viewerIdentityId;

    public UnknownViewIdentityId(@NonNull UUID viewerIdentityId) {
        super("Could not find any viewer identity with id='"+viewerIdentityId+"'");
        this.viewerIdentityId = viewerIdentityId;
    }
}
