package perobobbot.data.service;

import lombok.NonNull;
import perobobbot.lang.*;

import java.time.Duration;
import java.util.UUID;

public interface BankService {

    int VERSION = 1;

    /**
     * @param platformUserId the id of the platform user
     * @param channelId the id of the channel (its name for Twitch for instance)
     * @return the safe on the provided channel associated with the provided platform user id
     */
    @NonNull Safe findSafe(@NonNull UUID platformUserId, @NonNull String channelId);

    /**
     *
     * @param platform the platform the viewer belong to
     * @param userId the id of the viewer on the provided platform
     * @param channelId the id of the channel (its name for Twitch for instance)
     * @return the safe on the provided channel associated with the user on the provided platform with the given id
     */
    @NonNull Safe findSafe(@NonNull Platform platform, @NonNull String userId, @NonNull String channelId);

    /**
     * @param uuid the id of the requested safe
     * @return the safe with the requested id
     */
    @NonNull Safe getSafe(@NonNull UUID uuid);

    /**
     * Add some point to a safe
     * @param safeId the id of the safe to which points must be added
     * @param pointType the type of the points
     * @param amount the amount to add
     * @return the new balance of the safe
     */
    @NonNull Balance addPoints(@NonNull UUID safeId, @NonNull PointType pointType, long amount);

    /**
     * Prepare a transaction. A transaction is used to perform an operation safely.
     * A transaction will take some credit from a safe. The transaction can then be completed
     * (it will be closed and deleted) or cancelled (it will be closed and deleted but the credit
     * requested by this transaction will be put back into the safe).
     *
     * A transaction has a duration after which it is automatically cancelled if not completed before.
     *
     * @param safeId ths id of the safe to which the transaction applies
     * @param pointType the type of the points
     * @param requestedAmount the requested amount for the transaction
     * @param duration the duration of the transaction
     * @return some information about the transaction
     */
    @NonNull TransactionInfo createTransaction(@NonNull UUID safeId, @NonNull PointType pointType, long requestedAmount, @NonNull Duration duration);

    /**
     * Cancel the transaction with the provided id
     * @param transactionId the id of the transaction to cancel
     */
    void cancelTransaction(@NonNull UUID transactionId);

    /**
     * Complete the transaction with the provided id
     * @param transactionId the id of the transaction to complete
     */
    void completeTransaction(@NonNull UUID transactionId);

    /**
     * Cancel all transactions the last longer than then duration without being cancelled
     */
    void cleanTransactions();


}
