package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;

public class UnknownUser extends DataException {

    @NonNull
    @Getter
    private final String login;

    public UnknownUser(@NonNull String login) {
        super("Could not find any user with login='"+login+"'");
        this.login = login;
    }
}
