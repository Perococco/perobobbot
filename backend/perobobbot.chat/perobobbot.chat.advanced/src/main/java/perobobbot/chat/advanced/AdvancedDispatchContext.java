package perobobbot.chat.advanced;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.DispatchContext;

import java.time.Instant;

@Value
public class AdvancedDispatchContext implements DispatchContext {

    public static final DispatchContext NIL = new AdvancedDispatchContext(Instant.MIN);

    @NonNull Instant dispatchingTime;
}
