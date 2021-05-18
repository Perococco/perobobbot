package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.Platform;

public class UnknownViewIdentity extends DataException {

    @NonNull
    @Getter
    private final Platform platform;

    @NonNull
    @Getter
    private final String viewerId;

    public UnknownViewIdentity(@NonNull Platform platform, @NonNull String viewerId) {
        super("Could not find any viewer identity for platform='"+platform+"' and id='"+viewerId+"'");
        this.platform = platform;
        this.viewerId = viewerId;
    }
}
