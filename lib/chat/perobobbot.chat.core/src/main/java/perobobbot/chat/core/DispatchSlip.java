package perobobbot.chat.core;

import lombok.NonNull;
import perococco.perobobbot.chat.core.SimpleDispatchSlip;

import java.time.Instant;

public interface DispatchSlip {

    public static @NonNull DispatchSlip with(@NonNull MessageChannelIO messageChannelIO, @NonNull Instant dispatchTime) {
        return new SimpleDispatchSlip(messageChannelIO,dispatchTime);
    }

    /**
     * @return the channel used to send to message
     */
    @NonNull MessageChannelIO getMessageChannelIO();

    /**
     * @return the time the message was sent (basically when it left the bot to the iiIInteEErneEEt)
     */
    @NonNull Instant getDispatchTime();
}
