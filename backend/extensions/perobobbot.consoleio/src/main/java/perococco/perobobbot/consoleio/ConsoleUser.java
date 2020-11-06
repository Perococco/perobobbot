package perococco.perobobbot.consoleio;

import lombok.NonNull;
import perobobbot.common.lang.Role;
import perobobbot.common.lang.User;

public class ConsoleUser implements User {

    @Override
    public @NonNull String getUserId() {
        return "CONSOLE_USER";
    }

    @Override
    public @NonNull String getUserName() {
        return "console";
    }

    @Override
    public @NonNull String getHighlightedUserName() {
        return "console";
    }

    @Override
    public boolean canActAs(@NonNull Role role) {
        return true;
    }
}
