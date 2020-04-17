package bot.chat.core;

import bot.common.lang.ServiceLoaderHelper;
import lombok.NonNull;

import java.util.ServiceLoader;

/**
 * @author perococco
 **/
public abstract class ReconnectingChatClientFactory {

    @NonNull
    public abstract ChatClient createReconnectingChatClient(
            @NonNull ChatClient chatClient,
            @NonNull ReconnectionPolicy policy
    );


    @NonNull
    static ReconnectingChatClientFactory getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final ReconnectingChatClientFactory INSTANCE = ServiceLoaderHelper.getService(ServiceLoader.load(ReconnectingChatClientFactory.class));
    }
}
