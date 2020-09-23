package perobobbot.program.core;

import lombok.NonNull;
import lombok.Value;
import perobobbot.common.lang.User;

@Value
public class ChannelInfo {

    @NonNull
    String service;

    @NonNull
    String name;

    public boolean isOwnedBy(@NonNull User user) {
        return user.getUserId().equals(name);
    }
}
