package perobobbot.server.config.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import perobobbot.lang.PluginService;
import perobobbot.lang.UserAuthenticator;
import perobobbot.server.config.security.jwt.JwtAuthentication;

@Component
@RequiredArgsConstructor
@PluginService(type = UserAuthenticator.class, apiVersion = UserAuthenticator.VERSION,sensitive = false)
public class BasicUserAuthenticator implements UserAuthenticator {

    private final @NonNull perobobbot.security.core.UserProvider userProvider;
    private final @NonNull PasswordEncoder passwordEncoder;

    @Override
    public boolean authenticate(@NonNull String login, @NonNull String secret) {
        final var user = userProvider.getUserDetails(login);
        final var matches = passwordEncoder.matches(secret,user.getPassword());
        if (matches) {
            SecurityContextHolder.getContext().setAuthentication(JwtAuthentication.create(user));
            return true;
        }
        return false;
    }

    @Override
    public void clearAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
