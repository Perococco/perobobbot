package perobobbot.oauth;

import lombok.NonNull;
import perobobbot.lang.Secret;

import java.util.Optional;

public interface ApiToken {

    @NonNull String getClientId();

    @NonNull Secret getAccessToken();
}
