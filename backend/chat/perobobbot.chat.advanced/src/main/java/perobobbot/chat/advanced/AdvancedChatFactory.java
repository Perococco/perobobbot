package perobobbot.chat.advanced;

import lombok.NonNull;
import perobobbot.chat.core.Chat;
import perobobbot.common.lang.ServiceLoaderHelper;

import java.util.ServiceLoader;

/**
 * @author perococco
 **/
public abstract class AdvancedChatFactory {

    /**
     * Create an {@link AdvancedChat} base on the provided {@link Chat}.
     *
     * @param chat the chat to used to create the <code>AdvancedChat</code>
     * @param matcher the matcher used to match request and message from the chat
     * @param messageConverter used to convert text message to the type of the message of the chat
     * @param <M> the type of the message of the chat

     * @return an <code>AdvancedChat</code> base on the provided parameters
     */
    @NonNull
    public abstract <M> AdvancedChat<M> createBasedOn(
            @NonNull Chat chat,
            @NonNull RequestAnswerMatcher<M> matcher,
            @NonNull MessageConverter<M> messageConverter
    );

    @NonNull
    public static AdvancedChatFactory getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {

        private static final AdvancedChatFactory INSTANCE = ServiceLoaderHelper.getService(ServiceLoader.load(AdvancedChatFactory.class));
    }

}
