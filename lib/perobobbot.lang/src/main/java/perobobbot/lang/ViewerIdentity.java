package perobobbot.lang;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@TypeScript
public class ViewerIdentity {
    @NonNull UUID id;

    @NonNull Platform platform;
    /**
     * a unique identifier of the viewer (generally a UUID, a long or the login if it cannot
     * be changed)
     */
    @NonNull String viewerId;
    /**
     * the login of the viewer. Not used as id directly as some platform allow
     * the viewer to change his login (like Twitch)
     */
    @NonNull String login;
    /**
     * The pseudo of the viewer. Might be different that the login (Twitch allow
     * the change of the case of the login)
     */
    @NonNull String pseudo;
    
}
