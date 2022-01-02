package perobobbot.data.security.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.UpdateUserParameters;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.UserService;
import perobobbot.lang.Platform;
import perobobbot.lang.PluginService;
import perobobbot.security.com.User;

import java.util.Optional;

@Service
@SecuredService
@RequiredArgsConstructor
@PluginService(type = UserService.class, apiVersion = UserService.VERSION,sensitive = true)
public class SecuredUserService implements UserService {

    private final @NonNull@EventService UserService delegate;

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull User getUser(@NonNull String login) {
        return delegate.getUser(login);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull Optional<User> findUser(@NonNull String login) {
        return delegate.findUser(login);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public boolean doesUserExist(@NonNull String login) {
        return delegate.doesUserExist(login);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull User createUser(@NonNull CreateUserParameters parameters) {
        return delegate.createUser(parameters);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull ImmutableList<User> listAllUser() {
        return delegate.listAllUser();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull User updateUser(@NonNull String login, @NonNull UpdateUserParameters parameters) {
        return delegate.updateUser(login,parameters);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public void changePassword(@NonNull String login, @NonNull String newPassword) {
        delegate.changePassword(login,newPassword);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull ImmutableSet<String> findLoginOfUsersAuthenticatedWithViewerId(@NonNull Platform platform, @NonNull String viewerId) {
        return delegate.findLoginOfUsersAuthenticatedWithViewerId(platform,viewerId);
    }
}
