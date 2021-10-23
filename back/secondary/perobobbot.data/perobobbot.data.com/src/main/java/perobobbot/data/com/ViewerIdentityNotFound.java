package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.PerobobbotException;
import perobobbot.lang.Platform;

public class ViewerIdentityNotFound extends PerobobbotException {

    @Getter
    private final @NonNull Platform platform;
    @Getter
    private final @NonNull String userInfo;

    public ViewerIdentityNotFound(Platform platform, String userInfo) {
        super("Could not find any viewer identity with userInfo='"+userInfo+"' on platform '"+platform+"'");

        this.platform = platform;
        this.userInfo = userInfo;
    }
}
