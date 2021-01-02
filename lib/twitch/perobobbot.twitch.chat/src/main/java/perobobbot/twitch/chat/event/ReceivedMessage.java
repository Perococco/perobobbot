package perobobbot.twitch.chat.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.ChannelInfo;
import perobobbot.lang.MessageContext;
import perobobbot.lang.Platform;
import perobobbot.lang.User;
import perobobbot.twitch.chat.TwitchChatState;
import perobobbot.twitch.chat.TwitchUser;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.from.PingFromTwitch;
import perobobbot.twitch.chat.message.from.PrivMsgFromTwitch;

import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public class ReceivedMessage<M extends MessageFromTwitch> implements TwitchChatEvent {

    /**
     * The state of the Twitch chat when the message was received
     */
    @NonNull
    private final TwitchChatState state;

    /**
     * The time when the message was received
     */
    @NonNull
    private final Instant receptionTime;

    /**
     * The received message
     */
    @NonNull
    private final M message;

    @Override
    public String toString() {
        return "ReceivedMessages{" +
               "message=" + message +
               '}';
    }

    @Override
    public <T> @NonNull T accept(@NonNull TwitchChatEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean isPing() {
        return message instanceof PingFromTwitch;
    }

    @NonNull
    public String getRawMessage() {
        return message.getIrcParsing().getRawMessage();
    }

    @NonNull
    public Optional<MessageContext> toMessage() {
        if (message instanceof PrivMsgFromTwitch) {
            final PrivMsgFromTwitch privateMsg = (PrivMsgFromTwitch) this.message;
            final User owner = TwitchUser.createFromPrivateMessage(privateMsg);
            final boolean messageFromMe = state.getNickOfConnectedUser().equals(owner.getUserId());
            final ChannelInfo channelInfo = new ChannelInfo(Platform.TWITCH,privateMsg.getChannelName());
            return Optional.ofNullable(MessageContext.builder()
                                                     .chatConnectionInfo(state.getChatConnectionInfo())
                                                     .content(message.getPayload())
                                                     .messageFromMe(messageFromMe)
                                                     .messageOwner(owner)
                                                     .rawPayload(message.getIrcParsing().getRawMessage())
                                                     .receptionTime(receptionTime)
                                                     .channelInfo(channelInfo)
                                                     .build());
        }
        return Optional.empty();
    }



}
