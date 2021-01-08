package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.Platform;

@Getter
public class DuplicateCredentialForUser extends DataException {

    private final @NonNull String login;
    private final @NonNull Platform platform;
    private final @NonNull String credentialLogin;


    public DuplicateCredentialForUser(@NonNull String login, @NonNull Platform platform, @NonNull String credentialLogin) {
        super("A credential on platform '"+platform+"' with login '"+credentialLogin+"' exists already for user '"+login+"'");
        this.login = login;
        this.platform = platform;
        this.credentialLogin = credentialLogin;
    }
}
