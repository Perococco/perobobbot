package perobobbot.security.com;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.NoTypeScript;
import perobobbot.lang.TypeScript;

import java.util.Locale;

@NoTypeScript
@Value
@Builder
public class User {

    @NonNull
    String login;

    @NonNull
    String password;

    @NonNull
    Locale locale;

    @NonNull
    String jwtClaim;

    @NonNull
    ImmutableSet<RoleKind> roles;

    @NonNull
    ImmutableSet<Operation> operations;

    public @NonNull SimpleUser simplify() {
        return new SimpleUser(login,locale,roles);
    }

}


