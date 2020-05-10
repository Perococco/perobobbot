package bot.common.lang;

import lombok.NonNull;

public interface User {

    /**
     * @return an identifier of the user (for Twitch is can by its login)
     */
    @NonNull
    String getUserId();

    /**
     * @return the name of the user (for Twitch it can ben the display name of the user which
     * might be different that its login).
     */
    @NonNull
    String getUserName();

    boolean canActAs(@NonNull UserRole userRole);
}
