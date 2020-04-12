package bot.chat.core;

import bot.common.lang.Prioritized;
import bot.common.lang.ServiceLoaderHelper;
import lombok.NonNull;

import java.util.ServiceLoader;
import java.util.function.BiFunction;

/**
 * @author perococco
 **/
public interface ReconnectingChatClientFactory extends Prioritized {

    @NonNull
    ChatClient createReconnectingChatClient(@NonNull ChatClient chatClient, @NonNull ReconnectionPolicy policy);

    @NonNull
    static ReconnectingChatClientFactory with(@NonNull BiFunction<? super ChatClient, ? super ReconnectionPolicy,? extends ChatClient> factory) {
        return with(factory, Integer.MIN_VALUE);
    }

    @NonNull
    static ReconnectingChatClientFactory with(@NonNull BiFunction<? super ChatClient, ? super ReconnectionPolicy,? extends ChatClient> factory, int priority) {
        return new ReconnectingChatClientFactory() {
            @Override
            public @NonNull ChatClient createReconnectingChatClient(@NonNull ChatClient chatClient,
                    @NonNull ReconnectionPolicy policy) {
                return factory.apply(chatClient,policy);
            }

            @Override
            public int priority() {
                return priority;
            }
        };
    }

    @NonNull
    static ReconnectingChatClientFactory getInstance() {
        return ServiceLoaderHelper.getService(ServiceLoader.load(ReconnectingChatClientFactory.class));
    }
}
