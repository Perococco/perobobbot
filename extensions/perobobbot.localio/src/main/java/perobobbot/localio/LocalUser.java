package perobobbot.localio;

import lombok.NonNull;
import perobobbot.lang.ChatUser;
import perobobbot.lang.Platform;
import perobobbot.lang.Role;

import java.awt.*;
import java.util.Optional;

public class LocalUser implements ChatUser {

    public static final Color LOCAL_COLOR = new Color(212, 146, 239);

    @Override
    public @NonNull String getUserId() {
        return "LOCAL_USER";
    }

    @Override
    public @NonNull Platform getPlatform() {
        return Platform.LOCAL;
    }

    @Override
    public @NonNull String getUserName() {
        return "Perobobbot";
    }

    @Override
    public @NonNull String getHighlightedUserName() {
        return "perobobbot";
    }

    @Override
    public boolean canActAs(@NonNull Role role) {
        return true;
    }

    @Override
    public @NonNull Optional<Color> findUserColor() {
        return Optional.of(LOCAL_COLOR);
    }

}
