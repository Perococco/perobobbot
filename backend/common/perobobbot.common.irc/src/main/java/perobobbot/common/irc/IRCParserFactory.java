package perobobbot.common.irc;

import perobobbot.common.lang.ServiceLoaderHelper;
import lombok.NonNull;

import java.util.ServiceLoader;

/**
 * @author perococco
 **/
public abstract class IRCParserFactory {

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
