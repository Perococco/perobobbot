package perobobbot.consoleio;

import lombok.NonNull;
import perobobbot.common.lang.Role;
import perobobbot.common.lang.User;

public class LocalUser implements User {

    @Override
    public @NonNull String getUserId() {
        return "LOCAL_USER";
    }

    @Override
    public @NonNull String getUserName() {
        return "local";
    }

    @Override
    public @NonNull String getHighlightedUserName() {
        return "local";
    }

    @Override
    public boolean canActAs(@NonNull Role role) {
        return true;
    }
}
