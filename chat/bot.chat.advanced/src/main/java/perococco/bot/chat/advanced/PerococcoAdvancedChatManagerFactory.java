package perococco.bot.chat.advanced;

import bot.chat.advanced.*;
import bot.chat.core.ChatManager;
import bot.common.lang.Priority;
import lombok.NonNull;

/**
 * @author perococco
 **/
@Priority(Integer.MIN_VALUE)
public class PerococcoAdvancedChatManagerFactory extends AdvancedChatManagerFactory {

    @Override
    public @NonNull <M> AdvancedChatManager<M> createBasedOn(@NonNull ChatManager chatManager,
                                                             @NonNull RequestAnswerMatcher<M> matcher,
                                                             @NonNull MessageConverter<M> messageConverter) {
        return new PerococcoAdvancedChatManager<>(chatManager, matcher, messageConverter);
    }

}
