package bot.common.irc;

import bot.common.lang.Prioritized;
import bot.common.lang.ServiceLoaderHelper;
import lombok.NonNull;

import java.util.ServiceLoader;

/**
 * @author perococco
 **/
public interface IRCParserFactory extends Prioritized {

    @NonNull
    IRCParser create();

    static IRCParserFactory getInstance() {
        return ServiceLoaderHelper.getService(ServiceLoader.load(IRCParserFactory.class));
    }
}
