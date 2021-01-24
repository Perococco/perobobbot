package perobobbot.security.com;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import perobobbot.lang.NoTypeScript;

import java.util.Locale;

@NoTypeScript
@Value
@Builder
public class User {

    @NonNull
    String login;

    @NonNull
    String password;

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
        return new SimpleUser(login, locale, deactivated, roles);
    }

}


