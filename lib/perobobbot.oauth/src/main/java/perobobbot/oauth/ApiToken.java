package perobobbot.oauth;

import lombok.NonNull;
import perobobbot.lang.Secret;

public interface ApiToken {

    @NonNull String getClientId();

    @NonNull Secret getAccessToken();
}
