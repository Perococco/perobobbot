package perobobbot.data.com;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class SimpleUser {

    @NonNull
    String login;

    @Builder
    public SimpleUser(@NonNull String login) {
        this.login = login;
    }
}


