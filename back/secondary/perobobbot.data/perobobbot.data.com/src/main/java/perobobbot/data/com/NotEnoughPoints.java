package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.PlatformUser;
import perobobbot.lang.PointType;

@Getter
public class NotEnoughPoints extends DataException {

    private final @NonNull PlatformUser platformUser;
    private final @NonNull String channelName;
    private final @NonNull PointType type;
    private final long requestedAmount;

    public NotEnoughPoints(@NonNull PlatformUser platformUser,
                           @NonNull String channelName,
                           @NonNull PointType type,
                           long requestedAmount) {
        super("Not enough '%s' credits for '%s' on channel '%s:%s':  request-amount=%d".formatted(
                type, platformUser.getLogin(), platformUser.getPlatform(), channelName, requestedAmount
        ));
        this.platformUser = platformUser;
        this.channelName = channelName;
        this.type = type;
        this.requestedAmount = requestedAmount;
    }
}
