package perococco.perobobbot.common.irc;

import lombok.NonNull;
import perobobbot.common.irc.IRCParser;
import perobobbot.common.irc.IRCParserFactory;
import perobobbot.lang.Priority;

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
