package perobobbot.common.lang;

import com.google.common.collect.ImmutableCollection;
import lombok.NonNull;

import java.util.Optional;

public interface User {

    /**
     * @return an identifier of the user (for Twitch it can be his login)
     */
    @NonNull
    String getUserId();

    /**
     * @return the name of the user (for Twitch it can be the displayed name of the user which
     * might be different that his login).
     */
    @NonNull
    String getUserName();

    @NonNull
    String getHighlightedUserName();

    boolean canActAs(@NonNull Role role);

    @NonNull
    default Optional<Role> findHighestRole(@NonNull ImmutableCollection<Role> roles) {
        return roles.stream()
                    .filter(this::canActAs)
                    .max(Role.HIGHER_LEVEL_ROLE);
    }
}
