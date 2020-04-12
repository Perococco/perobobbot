package perobobbot.bot.common.irc;

import bot.common.irc.IRCParser;
import bot.common.irc.IRCParserFactory;
import lombok.NonNull;

/**
 * @author perococco
 **/
public class PerococcoIRCParserFactory implements IRCParserFactory {

    @Override
    public @NonNull IRCParser create() {
        return new PerococcoIRCParser();
    }

    @Override
    public int priority() {
        return Integer.MIN_VALUE;
    }
}
