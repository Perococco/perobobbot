package perobobbot.bot.common.irc;

import bot.common.irc.IRCParser;
import bot.common.irc.IRCParserFactory;
import bot.common.lang.Priority;
import lombok.NonNull;

/**
 * @author perococco
 **/
@Priority(Integer.MIN_VALUE)
public class PerococcoIRCParserFactory extends IRCParserFactory {

    @Override
    public @NonNull IRCParser create() {
        return new PerococcoIRCParser();
    }

}
