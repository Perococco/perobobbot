package bot.twitch.chat;

import bot.common.lang.User;
import bot.common.lang.UserRole;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TwichUser implements User {

    @NonNull
    private final String userId;

    @Override
    public @NonNull String userId() {
        return userId;
    }

    @Override
    public @NonNull String userName() {
        //TODO should get user name from displayName tag
        return "??"+userId+"??";
    }

    @Override
    public boolean canActAs(@NonNull UserRole userRole) {
        //TODO retreive broadcaster and subscriber information from badges
        return true;
    }
}
