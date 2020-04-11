package bot.chat.core;

import lombok.NonNull;

import java.util.Comparator;
import java.util.ServiceLoader;
import java.util.function.BiFunction;

/**
 * @author perococco
 **/
public interface ReconnectingChatClientFactory {

    @NonNull
    ChatClient createReconnectingChatClient(@NonNull ChatClient chatClient, @NonNull ReconnectionPolicy policy);

    int priority();




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
        return ServiceLoader.load(ReconnectingChatClientFactory.class)
                     .stream()
                     .map(ServiceLoader.Provider::get)
                     .max(Comparator.comparingInt(ReconnectingChatClientFactory::priority))
                     .orElseThrow(() -> new RuntimeException("Could not find any implementation of "+ReconnectingChatClientFactory.class));
    }
}
