package perobobbot.greeter;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.lang.ChannelInfo;
import perobobbot.lang.User;

public interface GreetingMessageCreator {

    @NonNull
    ImmutableSet<String> create(@NonNull ChannelInfo channelInfo, @NonNull ImmutableSet<User> users);

}
