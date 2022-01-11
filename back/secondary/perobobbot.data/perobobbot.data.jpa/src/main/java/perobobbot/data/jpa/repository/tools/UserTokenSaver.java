package perobobbot.data.jpa.repository.tools;

import lombok.NonNull;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.oauth.Token;

import java.util.UUID;

public interface UserTokenSaver {

    /**
     * @param login the login of the bot user to attach the token
     * @param clientId the database id of the client the token is attached to
     * @param token the token to attach
     * @return the database user token
     */
    @NonNull DecryptedUserTokenView save(@NonNull String login, @NonNull UUID clientId, @NonNull Token token);


}
