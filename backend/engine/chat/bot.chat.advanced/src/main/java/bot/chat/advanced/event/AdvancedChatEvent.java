package bot.chat.advanced.event;

import lombok.NonNull;

/**
 * An event from an {@link bot.chat.advanced.AdvancedChat}.
 * @author perococco
 */
public interface AdvancedChatEvent<M> {

    @NonNull
    <T> T accept(@NonNull AdvancedChatEventVisitor<M,T> visitor);

}
