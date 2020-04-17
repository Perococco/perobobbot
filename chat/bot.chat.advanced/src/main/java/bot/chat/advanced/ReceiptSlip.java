package bot.chat.advanced;

import lombok.NonNull;

import java.time.Instant;

public interface ReceiptSlip<A> {

    @NonNull
    Instant dispatchingTime();

    @NonNull
    Instant receptionTime();

    @NonNull
    Request<A> sentRequest();

    @NonNull
    A answer();
}
