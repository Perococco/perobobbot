package perobobbot.data.service;

import lombok.NonNull;
import perobobbot.lang.*;

import java.time.Duration;
import java.util.UUID;

public interface PointService {

    @NonNull Safe findSafe(@NonNull String userId, @NonNull Platform platform, @NonNull String channelName, @NonNull PointType pointType);

    @NonNull Balance getBalance(@NonNull UUID safeId);

    @NonNull Transaction createTransaction(@NonNull UUID safeId, long requestedAmount, @NonNull Duration duration);

    @NonNull void cancelTransaction(@NonNull UUID transactionId);

    @NonNull void performTransaction(@NonNull UUID transactionId);

    @NonNull Balance addPoints(@NonNull UUID safeId, int amount);

}
