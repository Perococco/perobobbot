package perococco.bot.chat.advanced;

import bot.chat.advanced.*;
import bot.chat.core.Chat;
import bot.common.lang.Priority;
import lombok.NonNull;

/**
 * @author perococco
 **/
@Priority(Integer.MIN_VALUE)
public class PerococcoAdvancedChatManagerFactory extends AdvancedChatManagerFactory {

    @Override
    public @NonNull <M> AdvancedChat<M> createBasedOn(@NonNull Chat chat,
                                                      @NonNull RequestAnswerMatcher<M> matcher,
                                                      @NonNull MessageConverter<M> messageConverter) {
        return new PerococcoAdvancedChat<>(chat, matcher, messageConverter);
    }

}
