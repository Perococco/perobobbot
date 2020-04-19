package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.message.IRCCommand;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author perococco
 **/
@Getter
@RequiredArgsConstructor
@ToString
public class GenericKnownMessageFromTwitch implements KnownMessageFromTwitch {

    @NonNull
    private final IRCCommand command;

    @NonNull
    private final IRCParsing ircParsing;



}
