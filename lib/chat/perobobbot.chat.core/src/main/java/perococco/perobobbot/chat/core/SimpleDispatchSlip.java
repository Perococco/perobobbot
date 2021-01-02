package perococco.perobobbot.chat.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.core.DispatchSlip;
import perobobbot.lang.ChannelInfo;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
public class SimpleDispatchSlip implements DispatchSlip {

    private final @NonNull ChannelInfo channelInfo;
    private final @NonNull Instant dispatchTime;
}
