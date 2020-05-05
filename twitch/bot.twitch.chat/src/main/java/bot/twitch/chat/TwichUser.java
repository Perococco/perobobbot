package bot.twitch.chat;

import bot.common.lang.User;
import bot.common.lang.UserRole;
import bot.twitch.chat.message.TagKey;
import bot.twitch.chat.message.from.PrivMsgFromTwitch;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TwichUser implements User {


    @NonNull
    @Getter
    private final String userId;

    @NonNull
    @Getter
    private final String userName;

    @Override
    public boolean canActAs(@NonNull UserRole userRole) {
        //TODO retrieve broadcaster and subscriber information from badges
        return true;
    }

    public static User createFromPrivateMessage(@NonNull PrivMsgFromTwitch message) {
        final String userId = message.user();
        final String userName = message.getTag(TagKey.DISPLAY_NAME, userId);
        return new TwichUser(userId,userName);
    }


}
