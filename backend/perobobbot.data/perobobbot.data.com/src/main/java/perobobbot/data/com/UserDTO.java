package perobobbot.data.com;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class UserDTO {

    @NonNull
    String login;

    @Builder
    public UserDTO(@NonNull String login) {
        this.login = login;
    }
}


