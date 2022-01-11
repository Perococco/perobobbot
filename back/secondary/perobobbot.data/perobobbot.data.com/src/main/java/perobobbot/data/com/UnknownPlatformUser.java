package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.Platform;

public class UnknownPlatformUser extends DataException {

    @NonNull
    @Getter
    private final Platform platform;

    @NonNull
    @Getter
    private final String userId;

    public UnknownPlatformUser(@NonNull Platform platform, @NonNull String userId) {
        super("Could not find any user for platform='"+platform+"' and id='"+ userId +"'");
        this.platform = platform;
        this.userId = userId;
    }
}
