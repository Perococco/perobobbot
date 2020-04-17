package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.message.IRCCommand;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author perococco
 **/
@Getter
@RequiredArgsConstructor
public class GenericKnownMessageFromTwitch extends MessageFromTwitchBase implements KnownMessageFromTwitch {

    @NonNull
    private final IRCCommand command;

    @NonNull
    private final IRCParsing ircParsing;


}
