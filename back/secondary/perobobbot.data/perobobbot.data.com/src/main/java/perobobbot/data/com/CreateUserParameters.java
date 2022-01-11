package perobobbot.data.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.PasswordEncoder;
import perobobbot.lang.TypeScript;
import perobobbot.security.com.Authentication;

@Value
@TypeScript
public class CreateUserParameters {

    @NonNull String login;

    @NonNull Authentication authentication;

    @NonNull
    public CreateUserParameters withPasswordEncoded(@NonNull PasswordEncoder passwordEncoder) {
        return new CreateUserParameters(login, authentication.withPasswordEncoded(passwordEncoder));
    }

}
