package perobobbot.data.service;

import lombok.NonNull;
import perobobbot.lang.Platform;
import perobobbot.lang.ViewerIdentity;

import java.util.Optional;

public interface ViewerIdentityService {

    int VERSION = 1;

    @NonNull Optional<ViewerIdentity> findIdentity(@NonNull Platform platform, @NonNull String userInfo);

    @NonNull ViewerIdentity updateIdentity(@NonNull Platform platform, @NonNull String viewerId, @NonNull String newLogin, @NonNull String newPseudo);
}
