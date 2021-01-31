package perobobbot.lang;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class Safe {

    @NonNull UUID id;

    @NonNull Platform platform;

    @NonNull String channelName;

    @NonNull String userChatId;

    @NonNull PointType type;

}
