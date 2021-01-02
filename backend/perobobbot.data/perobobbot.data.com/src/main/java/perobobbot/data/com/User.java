package perobobbot.data.com;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.DTO;

@Value
@Builder
@DTO
public class User {

    @NonNull
    String login;

    @NonNull
    String password;

    @NonNull
    String jwtClaim;

    @NonNull
    ImmutableSet<Role> roles;

    public @NonNull SimpleUser simplify() {
        return new SimpleUser(login,roles);
    }

}


