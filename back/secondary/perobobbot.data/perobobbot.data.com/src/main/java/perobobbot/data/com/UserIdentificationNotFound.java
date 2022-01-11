package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.PerobobbotException;
import perobobbot.lang.Platform;

public class UserIdentificationNotFound extends PerobobbotException {

    @Getter
    private final @NonNull Platform platform;
    @Getter
    private final @NonNull String userInfo;

    public UserIdentificationNotFound(Platform platform, String userInfo) {
        super("Could not find any platform user with userInfo='"+userInfo+"' on platform '"+platform+"'");

        this.platform = platform;
        this.userInfo = userInfo;
    }
}
