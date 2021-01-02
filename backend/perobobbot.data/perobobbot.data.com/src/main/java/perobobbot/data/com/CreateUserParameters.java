package perobobbot.data.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.DTO;
import perobobbot.lang.PasswordEncoder;
import perobobbot.lang.fp.Function1;

@Value
@DTO
public class CreateUserParameters {

    @NonNull String login;

    @NonNull String password;

    @NonNull
    public CreateUserParameters withPasswordEncoded(@NonNull PasswordEncoder passwordEncoder) {
        return new CreateUserParameters(login,passwordEncoder.encode(password));
    }

}
