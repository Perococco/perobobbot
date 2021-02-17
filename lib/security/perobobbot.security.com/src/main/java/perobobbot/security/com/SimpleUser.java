package perobobbot.security.com;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.TypeScript;

import java.beans.Transient;
import java.util.Locale;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    public @NonNull String getRolesAsString() {
        return roles.stream().map(Enum::name).collect(Collectors.joining(","));
    }


}


