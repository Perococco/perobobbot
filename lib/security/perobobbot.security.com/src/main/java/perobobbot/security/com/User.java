package perobobbot.security.com;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import perobobbot.lang.NoTypeScript;
import perobobbot.lang.Platform;

import java.util.Locale;
import java.util.Optional;

@NoTypeScript
@Value
@Builder
public class User implements UsernameProvider {

    @NonNull
    String login;

    @NonNull
    Authentication authentication;

    boolean deactivated;

    @NonNull
    Locale locale;

    @NonNull
    String jwtClaim;

    @NonNull
    @Singular
    ImmutableSet<RoleKind> roles;

    @NonNull
    @Singular
    ImmutableSet<Operation> operations;

    public @NonNull SimpleUser simplify() {
        return new SimpleUser(login, locale, deactivated, authentication.getMode(), roles);
    }

    @Override
    public @NonNull String getUsername() {
        return login;
    }

    public @NonNull Optional<String> getPassword() {
        return authentication.getPassword();
    }

    public @NonNull Optional<Platform> getIdentificationOpenIdPlatform() {
        return authentication.getOpenIdPlatform();
    }
}


