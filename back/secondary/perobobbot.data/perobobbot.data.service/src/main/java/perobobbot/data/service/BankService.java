package perobobbot.data.service;

import lombok.NonNull;
import perobobbot.lang.PointType;
import perobobbot.lang.*;

import java.time.Duration;
import java.util.UUID;

public interface BankService {

    int VERSION = 1;

    /**
     * @param viewerIdentityId the id of the viewer identity
     * @param channelName the name of the channel
     * @return the safe on the provided channel associated with the provided viewer identitity
     */
    @NonNull Safe findSafe(@NonNull UUID viewerIdentityId, @NonNull String channelName);

    /**
     *
     * @param platform the platform the viewer belong to
     * @param viewerId the id of the viewer on the provided platform
     * @param channelName the channel name
     * @return the safe on the provided channel associted with the viewer on the provided platform with the given id
     */
    @NonNull Safe findSafe(@NonNull Platform platform, @NonNull String viewerId, @NonNull String channelName);

    /**
     * @param uuid the id of the requested safe
     * @return the safe with the requested id
     */
    @NonNull Safe getSafe(@NonNull UUID uuid);

    @NonNull Balance addPoints(@NonNull UUID safeId, @NonNull PointType pointType, long amount);

    @NonNull TransactionInfo createTransaction(@NonNull UUID safeId, @NonNull PointType pointType, long requestedAmount, @NonNull Duration duration);

    void cancelTransaction(@NonNull UUID transactionId);

    void completeTransaction(@NonNull UUID transactionId);

    void cleanTransactions();


}
