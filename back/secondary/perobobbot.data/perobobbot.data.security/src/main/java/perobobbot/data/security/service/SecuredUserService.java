package perobobbot.data.security.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.UpdateUserParameters;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.UserService;
import perobobbot.security.com.User;

@Service
@SecuredService
@RequiredArgsConstructor
public class SecuredUserService implements UserService {

    private final @EventService UserService delegate;

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull User getUser(@NonNull String login) {
        return delegate.getUser(login);
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
}
