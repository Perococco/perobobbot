package perobobbot.lang.token;

import lombok.NonNull;

public interface UserTokenRefresher {

    @NonNull DecryptedUserTokenView getRefreshedUserToken();

}
