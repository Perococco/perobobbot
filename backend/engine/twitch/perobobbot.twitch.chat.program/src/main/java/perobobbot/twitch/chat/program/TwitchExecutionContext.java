package perobobbot.twitch.chat.program;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.common.lang.ChannelInfo;
import perobobbot.common.lang.Platform;
import perobobbot.common.lang.User;
import perobobbot.twitch.chat.TwitchChatIO;
import perobobbot.twitch.chat.TwitchUser;
import perobobbot.twitch.chat.event.ReceivedMessage;
import perobobbot.twitch.chat.message.from.PrivMsgFromTwitch;

public class TwitchExecutionContext  {

    @NonNull
    private final TwitchChatIO twitchChatIO;

    @NonNull
    private final ReceivedMessage<PrivMsgFromTwitch> reception;

    @Getter
    @NonNull
    private final User executingUser;

    @Getter
    @NonNull
    private final ChannelInfo channelInfo;

    public TwitchExecutionContext(@NonNull TwitchChatIO twitchChatIO, @NonNull ReceivedMessage<PrivMsgFromTwitch> reception) {
        this.twitchChatIO = twitchChatIO;
        this.reception = reception;
        this.executingUser = TwitchUser.createFromPrivateMessage(reception.getMessage());
        this.channelInfo = new ChannelInfo(Platform.TWITCH, reception.getMessage().getChannel().getName());
    }

}
