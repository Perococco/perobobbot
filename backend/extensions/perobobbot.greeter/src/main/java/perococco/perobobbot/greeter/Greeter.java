package perococco.perobobbot.greeter;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.ChannelIO;
import perobobbot.common.lang.ChannelInfo;
import perobobbot.common.lang.IO;
import perobobbot.common.lang.User;

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
        messageCreator.create(channelInfo,greeters).forEach(channelIO::print);
    }

}
