package perobobbot.data.service;

import lombok.NonNull;
import perobobbot.lang.*;

import java.time.Duration;
import java.util.UUID;

public interface BankService {

    int VERSION = 1;

    @NonNull Safe findSafe(@NonNull UUID viewerIdentityId, @NonNull String channelName);

    @NonNull Safe getSafe(@NonNull UUID uuid);

    @NonNull Balance addPoints(@NonNull UUID safeId, @NonNull PointType pointType, int amount);

    @NonNull TransactionInfo createTransaction(@NonNull UUID safeId, @NonNull PointType pointType, long requestedAmount, @NonNull Duration duration);

    void cancelTransaction(@NonNull UUID transactionId);

    void completeTransaction(@NonNull UUID transactionId);

    void cleanTransactions();


}
