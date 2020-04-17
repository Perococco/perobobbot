package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
@Getter
public class UnknownMessageFromTwitch extends MessageFromTwitchBase implements MessageFromTwitch {

    @NonNull
    private final IRCParsing ircParsing;

}
