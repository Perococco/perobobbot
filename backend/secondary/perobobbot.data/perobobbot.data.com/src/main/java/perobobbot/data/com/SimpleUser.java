package perobobbot.data.com;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.DTO;

@Value
@DTO
public class SimpleUser {

    @NonNull
    String login;

    @NonNull
    ImmutableSet<RoleKind> roles;

}

