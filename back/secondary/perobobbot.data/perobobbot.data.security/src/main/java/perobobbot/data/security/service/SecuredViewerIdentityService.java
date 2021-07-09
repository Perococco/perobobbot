package perobobbot.data.security.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.ViewerIdentityService;
import perobobbot.lang.Platform;
import perobobbot.lang.PluginService;
import perobobbot.lang.ViewerIdentity;

import java.util.Optional;

@Service
@SecuredService
@RequiredArgsConstructor
@PluginService(type = ViewerIdentityService.class,apiVersion = ViewerIdentityService.VERSION,sensitive = true)
public class SecuredViewerIdentityService implements ViewerIdentityService {

    private final @NonNull @EventService ViewerIdentityService delegate;

    @Override
    @PreAuthorize("isAuthenticated()")
    public @NonNull Optional<ViewerIdentity> findIdentity(@NonNull Platform platform, @NonNull String userInfo) {
        return delegate.findIdentity(platform, userInfo);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public @NonNull ViewerIdentity updateIdentity(@NonNull Platform platform, @NonNull String viewerId, @NonNull String newLogin, @NonNull String newPseudo) {
        return delegate.updateIdentity(platform, viewerId, newLogin, newPseudo);
    }
}
