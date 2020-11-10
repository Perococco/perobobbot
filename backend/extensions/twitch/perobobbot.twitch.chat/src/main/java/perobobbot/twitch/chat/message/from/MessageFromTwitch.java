package perobobbot.twitch.chat.message.from;

import lombok.NonNull;
import perobobbot.common.irc.IRCParsing;
import perobobbot.twitch.chat.TagsAndBadges;
import perobobbot.twitch.chat.message.TagKey;

import java.util.Optional;

/**
 * @author perococco
 **/
public interface MessageFromTwitch extends TagsAndBadges {

    @NonNull
    IRCParsing getIrcParsing();

    @NonNull
    default String getPayload() {
        return getIrcParsing().getRawMessage();
    }

    @Override
    default @NonNull Optional<String> findTag(@NonNull TagKey tagKey) {
        return getIrcParsing().tagValue(tagKey.getKeyName());
    }

    <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor);
}
