package perobobbot.data.com;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.TypeScript;

@TypeScript
@Value
@Builder
public class User {

    @NonNull
    String login;

    @NonNull
    String password;

    @NonNull
    String jwtClaim;

    @NonNull
    ImmutableSet<RoleKind> roles;

    @NonNull
    ImmutableSet<Operation> operations;

    public @NonNull SimpleUser simplify() {
        return new SimpleUser(login,roles);
    }

}


