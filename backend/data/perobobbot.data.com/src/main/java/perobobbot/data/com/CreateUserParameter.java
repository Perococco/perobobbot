package perobobbot.data.com;

import lombok.NonNull;
import lombok.Value;

@Value
public class CreateUserParameter {

    @NonNull String login;

    @NonNull String password;
}
