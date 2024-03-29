package perobobbot.chat.advanced.event;

import lombok.NonNull;

/**
 * Visitor for all {@link AdvancedChatEvent}
 * @param <M> the type of the message of the chat
 * @param <T> the type returned by the visitor
 */
public interface AdvancedChatEventVisitor<M,T> {

    @NonNull
    T visit(@NonNull Connection<M> event);

    @NonNull
    T visit(@NonNull Disconnection<M> event);

    @NonNull
    T visit(@NonNull PostedMessage<M> event);

    @NonNull
    T visit(@NonNull ReceivedMessage<M> event);

    @NonNull
    T visit(@NonNull Error<M> event);
}
