package perobobbot.greeter;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.core.ChannelIO;
import perobobbot.lang.ChannelInfo;
import perobobbot.chat.core.IO;
import perobobbot.lang.User;

@RequiredArgsConstructor
public class Greeter {

    @NonNull
    private final IO io;

    @NonNull
    private final GreetingMessageCreator messageCreator;

    @NonNull
    private final ChannelInfo channelInfo;

    @NonNull
    private final ImmutableSet<User> greeters;

    public void execute() {
        final ChannelIO channelIO = io.forChannelInfo(channelInfo);
        messageCreator.create(channelInfo,greeters).forEach(channelIO::send);
    }

}
