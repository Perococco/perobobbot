package bot.chat.advanced;

import bot.chat.core.ChatClient;
import bot.common.lang.Subscription;
import lombok.NonNull;

/**
 * @author perococco
 **/
public interface AdvancedChatClient {

    @NonNull
    static AdvancedChatClient basedOn(@NonNull ChatClient chatClient, @NonNull RequestAnswerMatcher matcher, @NonNull MessageConverter messageConverter) {
        return AdvancedChatClientFactory.getInstance().createBasedOn(chatClient, matcher, messageConverter);
    }

    @NonNull
    AdvancedChat connect();

    void disconnect();

    @NonNull
    Subscription addChatClientListener(@NonNull AdvancedChatClientListener listener);

}
