package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;

public class DuplicateUser extends DataException {

    @NonNull
    @Getter
    private final String login;

    public DuplicateUser(@NonNull String login) {
        super("A user with the login '"+login+"' exists already");
        this.login = login;
    }
}
