package bot.chat.advanced;

import bot.chat.core.ChatClient;
import bot.common.lang.ServiceLoaderHelper;
import lombok.NonNull;

import java.util.ServiceLoader;

/**
 * @author perococco
 **/
public abstract class AdvancedChatClientFactory {

    @NonNull
    public abstract <M> AdvancedChatClient<M> createBasedOn(@NonNull ChatClient chatClient, @NonNull RequestAnswerMatcher<M> matcher, @NonNull MessageConverter<M> messageConverter);

    @NonNull
    public static AdvancedChatClientFactory getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {

        private static final AdvancedChatClientFactory INSTANCE = ServiceLoaderHelper.getService(ServiceLoader.load(AdvancedChatClientFactory.class));
    }

}
