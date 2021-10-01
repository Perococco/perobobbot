package perobobbot.lang;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class IdentityInfo {

    @NonNull Platform getPlatform;
    /**
     * a unique identifier of the viewer (generally a UUID, a long or the login if it cannot
     * be changed)
     */
    @NonNull String getViewerId;
    /**
     * the login of the viewer. Not used as id directly as some platform allow
     * the viewer to change his login (like Twitch)
     */
    @NonNull String getLogin;
    /**
     * The pseudo of the viewer. Might be different that the login (Twitch allow
     * the change of the case of the login)
     */
    @NonNull String getPseudo;

}
