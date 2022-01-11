package perobobbot.data.service;

import lombok.NonNull;
import perobobbot.data.com.UserIdentificationNotFound;
import perobobbot.lang.Platform;
import perobobbot.lang.PlatformUser;
import perobobbot.lang.UserIdentification;
import perobobbot.lang.UserIdentity;

import java.util.Optional;

public interface PlatformUserService {

    int VERSION = 1;

    /**
     * Find a {@link UserIdentification} for a specified platform
     * @param platform the platform the identity
     * @param userInfo some information about the user, can be its pseudo or its login
     * @return the identity of the viewer found from the provided information
     */
    @NonNull Optional<PlatformUser> findPlatformUser(@NonNull Platform platform, @NonNull String userInfo);

    /**
     * Change the pseudo and login of a user
     * @param newIdentity the new identity of the user (the userId must not change though)
     * @return the updated user identification
     */
    @NonNull PlatformUser updateUserIdentity(@NonNull UserIdentity newIdentity);


    default @NonNull PlatformUser getPlatformUser(@NonNull Platform platform, @NonNull String userInfo) {
        return findPlatformUser(platform, userInfo).orElseThrow(() -> new UserIdentificationNotFound(platform,userInfo));
    }


}
