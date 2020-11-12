package perococco.perobobbot.irc;

import lombok.NonNull;
import perobobbot.irc.IRCParser;
import perobobbot.irc.IRCParserFactory;
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
