package perobobbot.greeter;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.core.MessageChannelIO;
import perobobbot.lang.ChannelInfo;
import perobobbot.chat.core.IO;
import perobobbot.lang.User;

@RequiredArgsConstructor
public class Greeter {

    @NonNull
    private final IO io;

    @NonNull
    private final GreetingMessageCreator messageCreator;

    private final String userId;

    @NonNull
    private final ChannelInfo channelInfo;

    @NonNull
    private final ImmutableSet<User> greeters;

    public void execute() {
        io.getMessageChannelIO(userId,channelInfo)
          .thenAccept(this::sendGreeting);
    }

    private void sendGreeting(@NonNull MessageChannelIO messageChannelIO) {
        messageCreator.create(channelInfo,greeters).forEach(messageChannelIO::send);
    }

}
