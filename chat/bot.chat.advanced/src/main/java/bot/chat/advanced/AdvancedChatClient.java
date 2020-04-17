package bot.chat.advanced;

import bot.chat.core.ChatClient;
import bot.common.lang.Subscription;
import lombok.NonNull;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author perococco
 **/
public interface AdvancedChatClient<M> {

    @NonNull
    static <M> AdvancedChatClient<M> basedOn(@NonNull ChatClient chatClient, @NonNull RequestAnswerMatcher<M> matcher, @NonNull MessageConverter<M> messageConverter) {
        return AdvancedChatClientFactory.getInstance().createBasedOn(chatClient, matcher, messageConverter);
    }

    @NonNull
    AdvancedChat<M> connect();

    void disconnect();

    @NonNull
    Subscription addChatClientListener(@NonNull AdvancedChatClientListener<M> listener);

}
