package perobobbot.chat.core;

import lombok.NonNull;

import java.time.Instant;

public interface DispatchSlip {

    /**
     * @return the channel used to send to message
     */
    @NonNull MessageChannelIO getMessageChannelIO();

    /**
     * @return the time the message was sent (basically when it left the bot to the iiIInteEErneEEt)
     */
    @NonNull Instant getDispatchTime();
}
