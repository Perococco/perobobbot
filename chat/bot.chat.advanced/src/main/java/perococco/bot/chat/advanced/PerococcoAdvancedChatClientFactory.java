package perococco.bot.chat.advanced;

import bot.chat.advanced.AdvancedChatClient;
import bot.chat.advanced.AdvancedChatClientFactory;
import bot.chat.advanced.MessageConverter;
import bot.chat.advanced.RequestAnswerMatcher;
import bot.chat.core.ChatClient;
import bot.common.lang.Priority;
import lombok.NonNull;

/**
 * @author perococco
 **/
@Priority(Integer.MIN_VALUE)
public class PerococcoAdvancedChatClientFactory extends AdvancedChatClientFactory  {

    @Override
    public @NonNull <M> AdvancedChatClient<M> createBasedOn(@NonNull ChatClient chatClient,
            @NonNull RequestAnswerMatcher<M> matcher, @NonNull MessageConverter<M> messageConverter) {
        return new PerococcoAdvancedChatClient<>(chatClient, matcher, messageConverter);
    }

}
