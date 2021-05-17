package perobobbot.rest.client;

import lombok.NonNull;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.security.com.Credential;
import perobobbot.security.com.JwtInfo;
import perobobbot.security.com.SimpleUser;

import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 */
public interface SecurityClient {

    /**
     * @return the authenticated user information
     */
    @NonNull CompletionStage<SimpleUser> getCurrentUser();

    /**
     * Authenticate the user with the provided credential and return a JWT for further authentication
     * @param credential the credentials to use to authenticate the user
     * @return a JWT for further authentication
     */
    @NonNull CompletionStage<JwtInfo> signIn(@NonNull Credential credential);

    /**
     * Sign up user
     * @param parameters the data used to create the user
     * @return the created user
     */
    @NonNull CompletionStage<SimpleUser> singup(@NonNull CreateUserParameters parameters);

}
