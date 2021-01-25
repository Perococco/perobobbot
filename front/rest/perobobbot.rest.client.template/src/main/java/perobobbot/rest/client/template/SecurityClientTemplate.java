package perobobbot.rest.client.template;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestOperations;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.rest.client.SecurityClient;
import perobobbot.security.com.Credential;
import perobobbot.security.com.JwtInfo;
import perobobbot.security.com.SimpleUser;

@RequiredArgsConstructor
public class SecurityClientTemplate implements SecurityClient {

    private final @NonNull RestOperations restOperations;

    @Override
    public @NonNull SimpleUser getCurrentUser() {
        return restOperations.getForObject("/user",SimpleUser.class);
    }

    @Override
    public @NonNull JwtInfo signIn(@NonNull Credential credential) {
        return restOperations.postForObject("/signin",credential,JwtInfo.class);
    }

    @Override
    public @NonNull SimpleUser singup(@NonNull CreateUserParameters parameters) {
        return restOperations.postForObject("/signup",parameters,SimpleUser.class);
    }
}
