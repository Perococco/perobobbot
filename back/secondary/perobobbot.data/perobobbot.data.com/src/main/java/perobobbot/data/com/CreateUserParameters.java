package perobobbot.data.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.PasswordEncoder;
import perobobbot.lang.TypeScript;
import perobobbot.security.com.Identification;

@Value
@TypeScript
public class CreateUserParameters {

    @NonNull String login;

    @NonNull Identification identification;

    @NonNull
    public CreateUserParameters withPasswordEncoded(@NonNull PasswordEncoder passwordEncoder) {
        return new CreateUserParameters(login,identification.withPasswordEncoded(passwordEncoder));
    }

}
