package perobobbot.chat.advanced.event;

import lombok.NonNull;
import perobobbot.chat.advanced.AdvancedChat;

/**
 * An event from an {@link AdvancedChat}.
 * @author perococco
 */
public interface AdvancedChatEvent<M> {

    @NonNull
    <T> T accept(@NonNull AdvancedChatEventVisitor<M,T> visitor);

}
