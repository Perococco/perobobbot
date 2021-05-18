package perobobbot.lang;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class Safe {

    @NonNull UUID id;

    @NonNull ViewerIdentity viewerIdentity;

    @NonNull String channelName;

    @NonNull PointType type;

    long credit;
}
