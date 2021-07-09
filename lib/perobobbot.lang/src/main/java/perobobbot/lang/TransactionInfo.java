package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class TransactionInfo {

    @NonNull UUID id;

    @NonNull UUID safeId;

    @NonNull PointType pointType;

    long requestedAmount;

    @NonNull TransactionState state;

    @NonNull Instant expirationTime;
}
