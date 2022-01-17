package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

public interface PlatformBot {

    @NonNull UUID getId();
    @NonNull Bot getBot();
    @NonNull PlatformUser getUser();

    default @NonNull String getBotName() {
        return getBot().getName();
    }

    default @NonNull Platform getPlatform() {
        return getUser().getPlatform();
    }

    default @NonNull UUID getPlatformUserId() {
        return getUser().getId();
    }

    default @NonNull String getBotOwnerLogin() {
        return getBot().getOwnerLogin();
    }
}
