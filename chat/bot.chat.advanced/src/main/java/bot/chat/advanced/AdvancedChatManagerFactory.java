package bot.chat.advanced;

import bot.chat.core.Chat;
import bot.chat.core.ChatManagerFactory;
import bot.chat.core.ReconnectionPolicy;
import bot.common.lang.ServiceLoaderHelper;
import lombok.NonNull;

import java.net.URI;
import java.util.ServiceLoader;

/**
 * @author perococco
 **/
public abstract class AdvancedChatManagerFactory {

    @NonNull
    public <M> AdvancedChat<M> createBasedOn(@NonNull URI chatAddress, @NonNull ReconnectionPolicy reconnectionPolicy, @NonNull RequestAnswerMatcher<M> matcher, @NonNull MessageConverter<M> messageConverter) {
        final Chat chat = ChatManagerFactory.getInstance().create(chatAddress, reconnectionPolicy);
        return createBasedOn(chat, matcher, messageConverter);
    }

    @NonNull
    public abstract <M> AdvancedChat<M> createBasedOn(@NonNull Chat chat, @NonNull RequestAnswerMatcher<M> matcher, @NonNull MessageConverter<M> messageConverter);

    @NonNull
    public static AdvancedChatManagerFactory getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {

        private static final AdvancedChatManagerFactory INSTANCE = ServiceLoaderHelper.getService(ServiceLoader.load(AdvancedChatManagerFactory.class));
    }

}
