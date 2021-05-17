package perobobbot.irc;

import lombok.NonNull;

/**
 * @author perococco
 **/
public interface IRCParser {

    @NonNull
    static IRCParser create() {
        return IRCParserFactory.getInstance().create();
    }

    @NonNull
    static IRCParser createForPlugin() {
        return IRCParserFactory.getInstance(IRCParser.class.getModule().getLayer()).create();
    }

    @NonNull
    IRCParsing parse(@NonNull String message);

}
