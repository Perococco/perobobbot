package perobobbot.lang;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class MessageContext {

    /**
     * This message might have been created by a {@link MessagePreprocessor} from a source message.
     * this property contains the latter if any.
     */
    MessageContext source;

    /**
     * @return true if the user that is at the origin of
     * the message is the one that is connected to the channel {@see #bot}
     */
    boolean messageFromMe;

    /**
     * @return information used to connect to the channel
     */
    @NonNull ChatConnectionInfo chatConnectionInfo;

    /**
     * @return the user that is at the origin of this message
     */
    @NonNull ChatUser messageOwner;

    /**
     * @return the instant of reception of the message
     */
    @NonNull Instant receptionTime;

    /**
     * @return the raw payload received from the chat (for Twitch this might include badges)
     */
    @NonNull String rawPayload;

    /**
     * @return the content of the private message
     */
    @NonNull String content;

    /**
     * @return information regarding the channel the message is coming from
     */
    @NonNull ChannelInfo channelInfo;

    /**
     * @return the id of the user that is at the origin of the message
     */

    public @NonNull String getMessageOwnerId() {
        return messageOwner.getUserId();
    }

    /**
     * @return the platform the message is coming from
     */
    public @NonNull Platform getPlatform() {
        return channelInfo.getPlatform();
    }

    /**
     * @return the name of channel the message is coming from
     */
    public @NonNull String getChannelName() {
        return channelInfo.getChannelName();
    }

    /**
     * @param prefix a prefix value
     * @return true if the message content starts with the provided prefix
     */
    public boolean doesContentStartWith(char prefix) {
        return !content.isEmpty() && content.charAt(0) == prefix;
    }

    public @NonNull Optional<MessageContext> getSource() {
        return Optional.ofNullable(source);
    }

    public MessageContextBuilder forPreprocessor() {
        return toBuilder().source(this);
    }

    public @NonNull UUID getBotId() {
        return chatConnectionInfo.getBotId();
    }
}
