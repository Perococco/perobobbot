package perobobbot.common.lang;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class MessageContext {

    /**
     * @return true if the user that is at the origin of
     * the message is me (as the one connected to the channel)
     */
    boolean messageFromMe;

    /**
     * @return the user that is at the origin of this message
     */
    @NonNull User messageOwner;

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
     * @return information regarding the channel the execution has been initiated from
     */
    @NonNull ChannelInfo channelInfo;

    @NonNull
    public String getMessageOwnerId() {
        return messageOwner.getUserId();
    }

    public Platform getPlatform() {
        return channelInfo.getPlatform();
    }

    public boolean doesContentStartWith(char prefix) {
        return !content.isEmpty() && content.charAt(0) == prefix;
    }
}
