package perobobbot.security.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.TypeScript;

@Value
@TypeScript
public class ChangePasswordParameters {
    @NonNull String login;
    @NonNull String password;
    @NonNull String newPassword;
}
