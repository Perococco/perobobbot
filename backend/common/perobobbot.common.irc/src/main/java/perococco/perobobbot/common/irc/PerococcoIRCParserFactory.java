package perococco.perobobbot.common.irc;

import perobobbot.common.irc.IRCParser;
import perobobbot.common.irc.IRCParserFactory;
import perobobbot.common.lang.Priority;
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
