package perobobbot.data.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.common.lang.fp.Function1;

@Value
public class CreateUserParameters {

    @NonNull String login;

    @NonNull String password;

    @NonNull
    public CreateUserParameters withPasswordEncoded(@NonNull Function1<? super String, ? extends String> passwordEncoder) {
        return new CreateUserParameters(login,passwordEncoder.f(password));
    }

}
