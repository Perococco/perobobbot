package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;


public class InvalidPlatformUserId extends DataException {

    @Getter
    private final @NonNull String userId;

    public InvalidPlatformUserId(@NonNull String userId) {
        super("The userId '%s' is invalid for the opertion".formatted(userId));
        this.userId = userId;
    }
}
