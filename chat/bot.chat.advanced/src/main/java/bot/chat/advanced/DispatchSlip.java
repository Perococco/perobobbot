package bot.chat.advanced;

import lombok.NonNull;

import java.time.Instant;

public interface DispatchSlip {

    @NonNull
    Command sentCommand();

    @NonNull
    Instant dispatchingTime();

}
