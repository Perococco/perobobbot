package perobobbot.lang.token;

import lombok.NonNull;
import perobobbot.lang.Secret;

import java.util.UUID;

public interface DecryptedTokenView extends TokenView<Secret> {

    default @NonNull String getAccessToken() {
        return getToken().getAccessToken().getValue();
    }

}
