package bot.common.irc;

import bot.common.lang.Prioritized;
import bot.common.lang.ServiceLoaderHelper;
import lombok.NonNull;

import java.util.ServiceLoader;

/**
 * @author perococco
 **/
public abstract class IRCParserFactory implements Prioritized {

    @NonNull
    public abstract IRCParser create();

    @NonNull
    static IRCParserFactory getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final IRCParserFactory INSTANCE = ServiceLoaderHelper.getService(ServiceLoader.load(IRCParserFactory.class));
    }
}
