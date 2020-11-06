package perococco.perobobbot.greeter;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.common.lang.ChannelInfo;
import perobobbot.common.lang.User;

public interface GreetingMessageCreator {

    @NonNull
    ImmutableSet<String> create(@NonNull ChannelInfo channelInfo, @NonNull ImmutableSet<User> users);

}
