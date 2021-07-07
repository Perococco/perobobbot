package perobobbot.twitch.api;

import lombok.NonNull;
import lombok.Value;

@Value
public class UserInfo {

    @NonNull String id;
    @NonNull String login;
    @NonNull String name;
}
