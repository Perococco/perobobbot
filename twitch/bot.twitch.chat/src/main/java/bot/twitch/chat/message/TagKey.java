package bot.twitch.chat.message;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public enum  TagKey {
    LOGIN("login"),
    BAN_DURATION("ban-duration"),
    TARGET_MSG_ID("target-msg-id"),
    MSG_ID("msg-id"),
    EMOTE_ONLY("emote-only"),
    FOLLOWERS_ONLY("followers-only"),
    R9K("r9k"),
    SLOW("slow"),
    SUBS_ONLY("subs-only");

    @NonNull
    private final String keyName;
}
