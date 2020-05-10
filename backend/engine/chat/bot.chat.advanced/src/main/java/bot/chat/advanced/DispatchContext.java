package bot.chat.advanced;

import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
public class DispatchContext {

    public static final DispatchContext NIL = new DispatchContext(Instant.MIN);

    @NonNull
    private final Instant dispatchingTime;
}
