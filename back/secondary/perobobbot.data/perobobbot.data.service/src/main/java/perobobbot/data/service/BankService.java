package perobobbot.data.service;

import lombok.NonNull;
import perobobbot.lang.*;

import java.time.Duration;
import java.util.UUID;

public interface BankService {

    @NonNull Safe findSafe(@NonNull UserOnChannel userOnChannel, @NonNull PointType pointType);

    void cleanTransactions();

    @NonNull Balance getBalance(@NonNull UserOnChannel userOnChannel, @NonNull PointType pointType);

    @NonNull Balance getBalance(@NonNull UUID safeId);

    @NonNull TransactionInfo createTransaction(@NonNull UUID safeId, long requestedAmount, @NonNull Duration duration);

    void cancelTransaction(@NonNull UUID transactionId);

    void completeTransaction(@NonNull UUID transactionId);

    @NonNull Balance addPoints(@NonNull UUID safeId, int amount);

}
