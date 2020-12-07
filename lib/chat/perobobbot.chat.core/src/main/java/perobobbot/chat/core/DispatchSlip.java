package perobobbot.chat.core;

import lombok.NonNull;

import java.time.Instant;

public interface DispatchSlip {

    @NonNull ChannelIO getChannelIO();

    @NonNull Instant getDispatchTime();
}
