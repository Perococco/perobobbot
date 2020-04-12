package bot.common.irc;

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
    IRCParsing parse(@NonNull String message);

}
