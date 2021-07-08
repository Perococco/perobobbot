package perobobbot.lang.token;

import lombok.NonNull;
import perobobbot.lang.Secret;

public interface DecryptedTokenView extends TokenView<Secret> {

    default @NonNull Secret getAccessToken() {
        return getToken().getAccessToken();
    }

}
