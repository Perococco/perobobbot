package perobobbot.data.security.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.PlatformUserService;
import perobobbot.lang.*;

import java.util.Optional;

@Service
@SecuredService
@RequiredArgsConstructor
@PluginService(type = PlatformUserService.class,apiVersion = PlatformUserService.VERSION,sensitive = true)
public class SecuredPlatformUserService implements PlatformUserService {

    private final @NonNull @EventService
    PlatformUserService delegate;

    @Override
    @PreAuthorize("isAuthenticated()")
    public @NonNull Optional<PlatformUser> findPlatformUser(@NonNull Platform platform, @NonNull String userInfo) {
        return delegate.findPlatformUser(platform, userInfo);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public @NonNull PlatformUser updateUserIdentity(@NonNull UserIdentity newIdentity) {
        return delegate.updateUserIdentity(newIdentity);
    }

}
