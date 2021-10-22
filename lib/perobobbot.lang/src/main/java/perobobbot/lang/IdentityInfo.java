package perobobbot.lang;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class IdentityInfo {

    @NonNull Platform platform;
    /**
     * a unique identifier of the viewer (generally a UUID, a long or the login if it cannot
     * be changed)
     */
    @NonNull String viewerId;


    public @NonNull Platform getPlatform() {
        return platform;
    }

    public @NonNull String getViewerId() {
        return viewerId;
    }

}
