package perobobbot.irc;

import lombok.NonNull;
import perobobbot.lang.ServiceLoaderHelper;

import java.util.ServiceLoader;

/**
 * @author perococco
 **/
public abstract class IRCParserFactory {

    @NonNull
    public abstract IRCParser create();

    @NonNull
    static IRCParserFactory getInstance() {
        return ServiceLoaderHelper.getService(ServiceLoader.load(IRCParserFactory.class));
    }

    @NonNull
    static IRCParserFactory getInstance(@NonNull ModuleLayer moduleLayer) {
        return ServiceLoaderHelper.getService(ServiceLoader.load(moduleLayer, IRCParserFactory.class));
    }

}
