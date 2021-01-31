package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class Transaction {

    @NonNull UUID id;

    @NonNull UUID safeId;

    long requestedAmount;

    TransactionState state;

    @NonNull Instant expirationTime;
}
