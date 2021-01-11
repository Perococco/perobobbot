package perobobbot.security.com;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.TypeScript;

@Value
@TypeScript
public class SimpleUser {

    @NonNull
    String login;

    @NonNull
    ImmutableSet<RoleKind> roles;



}


