package perobobbot.data.security.service;

import lombok.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.User;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.UserService;
import perobobbot.data.service.proxy.ProxyUserService;

@Service
@SecuredService
public class SecuredUserService extends ProxyUserService {

    public SecuredUserService(@NonNull @EventService UserService delegate) {
        super(delegate);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull User getUser(@NonNull String login) {
        return super.getUser(login);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull User createUser(@NonNull CreateUserParameters parameters) {
        return super.createUser(parameters);
    }
}
