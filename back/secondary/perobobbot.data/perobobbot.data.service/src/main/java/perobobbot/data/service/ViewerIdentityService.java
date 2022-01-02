package perobobbot.data.service;

import lombok.NonNull;
import perobobbot.data.com.ViewerIdentityNotFound;
import perobobbot.lang.Platform;
import perobobbot.lang.ViewerIdentity;

import java.util.Optional;

public interface ViewerIdentityService {

    int VERSION = 1;

    /**
     * Find a {@link ViewerIdentity} for a specified platform
     * @param platform the platform the identity
     * @param userInfo some information about the user, can be its pseudo or its login
     * @return the identity of the viewer found from the provided information
     */
    @NonNull Optional<ViewerIdentity> findIdentity(@NonNull Platform platform, @NonNull String userInfo);

    default @NonNull ViewerIdentity getIdentity(@NonNull Platform platform, @NonNull String userInfo) {
        return findIdentity(platform, userInfo).orElseThrow(() -> new ViewerIdentityNotFound(platform,userInfo));
    }

    @NonNull ViewerIdentity updateIdentity(@NonNull Platform platform, @NonNull String viewerId, @NonNull String newLogin, @NonNull String newPseudo);

}
