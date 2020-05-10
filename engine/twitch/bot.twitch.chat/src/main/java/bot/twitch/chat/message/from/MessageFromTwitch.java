package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.Badges;
import bot.twitch.chat.message.TagKey;
import bot.twitch.chat.message.Tags;
import lombok.NonNull;

import java.util.Optional;

/**
 * @author perococco
 **/
public interface MessageFromTwitch extends Tags, Badges {

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
