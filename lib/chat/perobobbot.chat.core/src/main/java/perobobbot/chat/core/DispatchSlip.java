package perobobbot.chat.core;

import lombok.NonNull;
import perobobbot.lang.ChannelInfo;
import perococco.perobobbot.chat.core.SimpleDispatchSlip;

import java.time.Instant;

public interface DispatchSlip {

    static @NonNull DispatchSlip with(@NonNull ChannelInfo channelInfo, @NonNull Instant dispatchTime) {
        return new SimpleDispatchSlip(channelInfo,dispatchTime);
    }

    /**
     * @return the information of channel the message originated from
     */
    @NonNull ChannelInfo getChannelInfo();

    /**
     * @return the time the message was sent (basically when it left the bot to the iiIInteEErneEEt)
     */
    @NonNull Instant getDispatchTime();
}
