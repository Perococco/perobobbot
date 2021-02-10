package perococco.perobobbot.chat.advanced;

import lombok.NonNull;
import perobobbot.chat.advanced.AdvancedChat;
import perobobbot.chat.advanced.AdvancedChatFactory;
import perobobbot.chat.advanced.MessageConverter;
import perobobbot.chat.advanced.RequestAnswerMatcher;
import perobobbot.chat.core.Chat;
import perobobbot.lang.Instants;
import perobobbot.lang.Priority;

/**
 * @author perococco
 **/
@Priority(Integer.MIN_VALUE)
public class PerococcoAdvancedChatFactory extends AdvancedChatFactory {

    @Override
    public @NonNull <M> AdvancedChat<M> createBasedOn(@NonNull Chat chat,
                                                      @NonNull RequestAnswerMatcher<M> matcher,
                                                      @NonNull MessageConverter<M> messageConverter,
                                                      @NonNull Instants instants) {
        return new PerococcoAdvancedChat<>(chat, matcher, messageConverter, instants);
    }

}
