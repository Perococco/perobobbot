package perobobbot.data.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.PasswordEncoder;

@Value
public class CreateUserParameters {

    @NonNull String login;

    @NonNull String password;

    @NonNull
    public CreateUserParameters withPasswordEncoded(@NonNull PasswordEncoder passwordEncoder) {
        return new CreateUserParameters(login,passwordEncoder.encode(password));
    }

}
