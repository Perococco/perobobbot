package perococco.perobobbot.chat.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.core.DispatchSlip;
import perobobbot.chat.core.MessageChannelIO;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
public class SimpleDispatchSlip implements DispatchSlip {

    private final @NonNull MessageChannelIO messageChannelIO;
    private final @NonNull Instant dispatchTime;
}
