package bot.chat.advanced;

import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

/**
 * The slip for the request sent to the chat
 * @param <A>
 */
public interface ReceiptSlip<A> {

    /**
     * @return the instant when the request was sent
     */
    @NonNull
    Instant dispatchingTime();

    /**
     * @return the instant when the answer of the request was received
     */
    @NonNull
    Instant receptionTime();

    /**
     * @return the sent request
     */
    @NonNull
    Request<A> sentRequest();

    /**
     * @return the received answer
     */
    @NonNull
    A answer();
}
