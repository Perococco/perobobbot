package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.PointType;
import perobobbot.lang.ViewerIdentity;

@Getter
public class NotEnoughPoints extends DataException {

    private final @NonNull ViewerIdentity viewerIdentity;
    private final @NonNull String channelName;
    private final @NonNull PointType type;
    private final long requestedAmount;

    public NotEnoughPoints(@NonNull ViewerIdentity viewerIdentity,
                           @NonNull String channelName,
                           @NonNull PointType type,
                           long requestedAmount) {
        super("Not enough credit: platform='%s' channel='%s' type='%s' user='%s' request-amount=%d".formatted(
                viewerIdentity.getPlatform(), channelName, type, viewerIdentity.getPseudo(), requestedAmount
        ));
        this.viewerIdentity = viewerIdentity;
        this.channelName = channelName;
        this.type = type;
        this.requestedAmount = requestedAmount;
    }
}
