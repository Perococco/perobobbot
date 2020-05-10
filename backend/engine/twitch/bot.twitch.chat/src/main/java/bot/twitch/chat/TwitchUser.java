package bot.twitch.chat;

import bot.common.lang.User;
import bot.common.lang.UserRole;
import bot.twitch.chat.message.TagKey;
import bot.twitch.chat.message.from.PrivMsgFromTwitch;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TwitchUser implements User {


    @NonNull
    @Getter
    private final String userId;

    @NonNull
    @Getter
    private final String userName;

    @NonNull
    private final Badges badges;

    @Override
    public boolean canActAs(@NonNull UserRole userRole) {
        if (userRole == UserRole.ANY_USER) {
            return true;
        } else if (badges.hasBadge(BadgeName.BROADCASTER)) {
            return true;
        } else if (badges.hasBadge(BadgeName.MODERATOR)) {
            return userRole != UserRole.THE_BOSS;
        } else if (badges.hasBadge(BadgeName.SUBSCRIBER)) {
            return userRole == UserRole.TRUSTED_USER;
        }
        return false;
    }

    public static User createFromPrivateMessage(@NonNull PrivMsgFromTwitch message) {
        final String userId = message.getUser();
        final String userName = message.getTag(TagKey.DISPLAY_NAME, userId);
        return new TwitchUser(userId, userName, message);
    }


}
