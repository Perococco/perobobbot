package perobobbot.greeter;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.core.IO;
import perobobbot.chat.core.MessageChannelIO;
import perobobbot.lang.ChannelInfo;
import perobobbot.lang.ChatConnectionInfo;
import perobobbot.lang.ChatUser;

@RequiredArgsConstructor
public class Greeter {

    @NonNull
    private final IO io;

    @NonNull
    private final GreetingMessageCreator messageCreator;

    @NonNull
    private final ChannelInfo channelInfo;

    private final ChatConnectionInfo chatConnectionInfo;

    @NonNull
    private final ImmutableSet<ChatUser> greeters;

    public void execute() {
        io.getMessageChannelIO(chatConnectionInfo, channelInfo)
          .thenAccept(this::sendGreeting);
    }

    private void sendGreeting(@NonNull MessageChannelIO messageChannelIO) {
        messageCreator.create(channelInfo,greeters).forEach(messageChannelIO::send);
    }

}
