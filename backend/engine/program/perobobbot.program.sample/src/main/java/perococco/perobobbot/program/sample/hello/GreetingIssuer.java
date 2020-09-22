package perococco.perobobbot.program.sample.hello;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.common.lang.User;
import perobobbot.program.core.ExecutionIO;

@Value
@EqualsAndHashCode(of = {"user", "channelId"})
public class GreetingIssuer {
    @NonNull User user;

    @NonNull String channelId;

    @NonNull ExecutionIO executionIO;

    public String getUserId() {
        return user.getUserId();
    }

    @NonNull
    public String getHighlightedUserName() {
        return user.getHighlightedUserName();
    }
}
