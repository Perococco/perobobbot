package perobobbot.twitch.chat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.User;
import perobobbot.common.lang.Role;
import perobobbot.twitch.chat.message.TagKey;
import perobobbot.twitch.chat.message.from.PrivMsgFromTwitch;

@RequiredArgsConstructor
@EqualsAndHashCode(of = {"userId"})
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
    public @NonNull String getHighlightedUserName() {
        return "@"+userName;
    }

    @Override
    public boolean canActAs(@NonNull Role role) {
        if (role == Role.ANY_USER) {
            return true;
        } else if (badges.hasBadge(BadgeName.BROADCASTER)) {
            return true;
        } else if (badges.hasBadge(BadgeName.MODERATOR)) {
            return role != Role.THE_BOSS;
        } else if (badges.hasBadge(BadgeName.SUBSCRIBER)) {
            return role == Role.TRUSTED_USER;
        }
        return false;
    }

    public static User createFromPrivateMessage(@NonNull PrivMsgFromTwitch message) {
        final String userId = message.getUser();
        final String userName = message.getTag(TagKey.DISPLAY_NAME, userId);
        return new TwitchUser(userId, userName, message);
    }


}
