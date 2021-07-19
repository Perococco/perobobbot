package perobobbot.lang;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class Safe {

    /**
     * id of the safe
     */
    @NonNull UUID id;

    /**
     * id of the viewer owning the safe
     */
    @NonNull ViewerIdentity viewerIdentity;

    /**
     * The name of the channel this safe applies to
     */
    @NonNull String channelName;

    /**
     * Credit for each credit type
     */
    @NonNull ImmutableMap<PointType,Long> credits;

    long getCredit(@NonNull String pointType) {
        return credits.getOrDefault(pointType,0L);
    }

}
