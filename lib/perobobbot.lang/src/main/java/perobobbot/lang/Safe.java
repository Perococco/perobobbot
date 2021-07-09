package perobobbot.lang;

import com.google.common.collect.ImmutableMap;
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

    @NonNull ImmutableMap<PointType,Long> credits;

    @NonNull long getCredit(@NonNull PointType pointType) {
        return credits.getOrDefault(pointType,0l);
    }

}
