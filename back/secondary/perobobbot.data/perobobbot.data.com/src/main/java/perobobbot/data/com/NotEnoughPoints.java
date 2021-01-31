package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.Platform;
import perobobbot.lang.PointType;

@Getter
public class NotEnoughPoints extends DataException {

    private final @NonNull Platform platform;
    private final @NonNull String channelName;
    private final @NonNull String userChatId;
    private final @NonNull PointType type;
    private final long requestedAmount;

    public NotEnoughPoints(@NonNull Platform platform,
                           @NonNull String channelName,
                           @NonNull String userChatId,
                           @NonNull PointType type,
                           long requestedAmount) {
        super("Not enough credit: platform='%s' channel='%s' type='%s' user='%s' request-amount=%d".formatted(
                platform, channelName, type, userChatId, requestedAmount
        ));
        this.platform = platform;
        this.channelName = channelName;
        this.userChatId = userChatId;
        this.type = type;
        this.requestedAmount = requestedAmount;
    }
}
