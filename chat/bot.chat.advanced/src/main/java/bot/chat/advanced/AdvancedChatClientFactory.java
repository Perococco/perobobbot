package bot.chat.advanced;

import bot.chat.core.ChatClient;
import lombok.NonNull;

import java.util.Comparator;
import java.util.ServiceLoader;

/**
 * @author perococco
 **/
public interface AdvancedChatClientFactory {

    @NonNull
    AdvancedChatClient createBasedOn(@NonNull ChatClient chatClient, @NonNull RequestAnswerMatcher matcher, @NonNull MessageConverter messageConverter);

    int priority();

    @NonNull
    static AdvancedChatClientFactory getInstance() {
        return ServiceLoader.load(AdvancedChatClientFactory.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .max(Comparator.comparingInt(AdvancedChatClientFactory::priority))
                .orElseThrow(() -> new RuntimeException("Could not find any implementation of "+AdvancedChatClientFactory.class));
    }

}
