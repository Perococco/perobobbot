package perobobbot.security.com;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.TypeScript;

import java.util.Locale;

@Value
@TypeScript
public class SimpleUser {

    @NonNull
    String login;

    @NonNull
    Locale locale;

    boolean deactivated;

    @NonNull
    ImmutableSet<RoleKind> roles;


}


