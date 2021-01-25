package perococco.perobobbot.rest.client.template;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.rest.client.SecurityClient;
import perobobbot.security.com.Credential;
import perobobbot.security.com.JwtInfo;
import perobobbot.security.com.SimpleUser;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class RestTemplateSecurityClient implements SecurityClient {

    private final @NonNull AsyncRestOperations restOperations;

    @Override
    public @NonNull CompletionStage<SimpleUser> getCurrentUser() {
        return restOperations.getForObject("/user",SimpleUser.class);
    }

    @Override
    public @NonNull CompletionStage<JwtInfo> signIn(@NonNull Credential credential) {
        return restOperations.postForObject("/signin",credential,JwtInfo.class);
    }

    @Override
    public @NonNull CompletionStage<SimpleUser> singup(@NonNull CreateUserParameters parameters) {
        return restOperations.postForObject("/signup",parameters,SimpleUser.class);
    }
}
