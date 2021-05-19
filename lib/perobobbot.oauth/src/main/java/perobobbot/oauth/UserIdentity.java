package perobobbot.oauth;

import lombok.NonNull;
import lombok.Value;

@Value
public class UserIdentity {

    @NonNull String userId;
    @NonNull String login;
}
