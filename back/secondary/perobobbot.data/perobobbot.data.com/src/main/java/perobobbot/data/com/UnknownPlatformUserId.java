package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.Platform;

import java.util.UUID;

public class UnknownPlatformUserId extends DataException {

    @NonNull
    @Getter
    private final UUID userId;

    public UnknownPlatformUserId(@NonNull UUID userId) {
        super("Could not find any platform user on with id='"+ userId +"'");
        this.userId = userId;
    }
}
