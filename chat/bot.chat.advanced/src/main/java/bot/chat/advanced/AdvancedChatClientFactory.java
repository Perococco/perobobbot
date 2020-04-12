package bot.chat.advanced;

import bot.chat.core.ChatClient;
import bot.common.lang.Prioritized;
import bot.common.lang.ServiceLoaderHelper;
import lombok.NonNull;

import java.util.ServiceLoader;

/**
 * @author perococco
 **/
public interface AdvancedChatClientFactory extends Prioritized {

    @NonNull
    AdvancedChatClient createBasedOn(@NonNull ChatClient chatClient, @NonNull RequestAnswerMatcher matcher, @NonNull MessageConverter messageConverter);

    @NonNull
    static AdvancedChatClientFactory getInstance() {
        return ServiceLoaderHelper.getService(ServiceLoader.load(AdvancedChatClientFactory.class));
    }

}
