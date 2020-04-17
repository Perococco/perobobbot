package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.message.IRCCommand;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
@Getter
public class UserState extends MessageFromTwitchBase implements KnownMessageFromTwitch {

    @NonNull
    private final IRCParsing ircParsing;

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.USERSTATE;
    }

}
