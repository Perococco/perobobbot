package perobobbot.lang;

import com.google.common.collect.ImmutableCollection;
import lombok.NonNull;

import java.awt.*;
import java.util.Comparator;
import java.util.Optional;

public interface ChatUser {

    Comparator<ChatUser> COMPARE_BY_ID = Comparator.comparing(ChatUser::getUserId).thenComparing(ChatUser::getPlatform);

    /**
     * @return an identifier of the user (for Twitch it is a long value)
     */
    @NonNull
    String getUserId();

    /**
     * @return the platform this use belongs to
     */
    @NonNull
    Platform getPlatform();

    /**
     * @return the name of the user (for Twitch it can be the displayed name of the user which
     * might be different that his login).
     */
    @NonNull
    String getUserName();

    @NonNull
    String getHighlightedUserName();

    @NonNull Optional<Color> findUserColor();

    boolean canActAs(@NonNull Role role);


    @NonNull
    default Optional<Role> findHighestRole(@NonNull ImmutableCollection<Role> roles) {
        return roles.stream()
                    .filter(this::canActAs)
                    .max(Role.HIGHER_LEVEL_ROLE_COMPARATOR);
    }

    default @NonNull Color getUserColor(@NonNull Color defaultColor) {
        return findUserColor().orElse(defaultColor);
    }


}
