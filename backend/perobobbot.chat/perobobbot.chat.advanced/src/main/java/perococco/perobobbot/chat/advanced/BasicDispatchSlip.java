package perococco.perobobbot.chat.advanced;

import lombok.NonNull;
import lombok.Value;
import perobobbot.chat.advanced.Command;
import perobobbot.chat.advanced.DispatchSlip;

import java.time.Instant;

@Value
public class BasicDispatchSlip implements DispatchSlip {

    @NonNull Instant dispatchingTime;

    @NonNull Command sentCommand;

}
