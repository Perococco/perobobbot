package bot.chat.advanced;

import bot.chat.core.ChatClient;
import bot.common.lang.Prioritized;
import bot.common.lang.ServiceLoaderHelper;
import lombok.NonNull;

import java.util.ServiceLoader;

/**
 * @author perococco
 **/
public abstract class AdvancedChatClientFactory implements Prioritized {

    @NonNull
    public abstract AdvancedChatClient createBasedOn(@NonNull ChatClient chatClient, @NonNull RequestAnswerMatcher matcher, @NonNull MessageConverter messageConverter);

    @NonNull
    public static AdvancedChatClientFactory getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {

        private static final AdvancedChatClientFactory INSTANCE = ServiceLoaderHelper.getService(ServiceLoader.load(AdvancedChatClientFactory.class));
    }

}
