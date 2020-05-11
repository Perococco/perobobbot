package perococco.perobobbot.chat.advanced;

import perobobbot.chat.advanced.ReceiptSlip;
import perobobbot.chat.advanced.Request;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
public class BasicReceiptSlip<A> implements ReceiptSlip<A> {

    @NonNull
    private final Instant dispatchingTime;

    @NonNull
    private final Instant receptionTime;

    @NonNull
    private final Request<A> sentRequest;

    @NonNull
    private final A answer;

}
