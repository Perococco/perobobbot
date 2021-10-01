package perobobbot.server.sse;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

public interface SSEventAccess {

    boolean isAllowed(@NonNull String login);

    @NonNull SSEventAccess PERMIT_ALL = l -> true;

    static @NonNull SSEventAccess fromSetOfAuthorizedLogins(@NonNull ImmutableSet<String> authorizedLogins) {
        return authorizedLogins::contains;
    }
}
