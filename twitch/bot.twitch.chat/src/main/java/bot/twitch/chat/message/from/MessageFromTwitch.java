package bot.twitch.chat.message.from;

import bot.chat.advanced.Message;
import bot.common.irc.IRCParsing;
import bot.twitch.chat.message.TagKey;
import bot.twitch.chat.message.Tags;
import lombok.NonNull;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author perococco
 **/
public interface MessageFromTwitch extends Message, Tags {

    @NonNull
    IRCParsing ircParsing();

    @Override
    @NonNull
    default String payload() {
        return ircParsing().rawMessage();
    }

    @Override
    default @NonNull Optional<String> findTag(@NonNull TagKey tagKey) {
        return ircParsing().tagValue(tagKey.name());
    }

    default <A extends MessageFromTwitch> boolean ifIsRightTypeUsePredicateOtherwiseFalse(@NonNull Class<A> expectedType, @NonNull Predicate<A> predicate) {
        if (expectedType.isInstance(this)) {
            return predicate.test(expectedType.cast(this));
        }
        return false;
    }
}
