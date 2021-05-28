package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.Platform;

public class UnknownClient extends DataException {

    @NonNull
    @Getter
    private final Platform platform;

    @NonNull
    @Getter
    private final String clientId;

    public UnknownClient(@NonNull Platform platform, @NonNull String clientId) {
        super("Could not find any client on platform '"+platform+"' with clientId='"+clientId+"'");
        this.platform = platform;
        this.clientId = clientId;
    }
}
