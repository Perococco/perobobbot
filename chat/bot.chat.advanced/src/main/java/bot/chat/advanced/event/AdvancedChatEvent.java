package bot.chat.advanced.event;

import lombok.NonNull;

/**
 * An event from an {@link bot.chat.advanced.AdvancedChat}.
 * @author perococco
 */
public interface AdvancedChatEvent<M> {

    void accept(@NonNull AdvancedChatEventVisitor<M> visitor);

}
