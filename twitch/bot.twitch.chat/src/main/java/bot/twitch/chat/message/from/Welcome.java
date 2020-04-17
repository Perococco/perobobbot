package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author perococco
 **/
@Getter
@RequiredArgsConstructor
public class Welcome implements MessageFromTwitch {

    @NonNull
    private final IRCParsing ircParsing;

}
