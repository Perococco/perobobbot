package bot.chat.advanced.event;

import lombok.NonNull;

public abstract class AdvancedChatEventAdapter<M,T> implements AdvancedChatEventVisitor<M,T> {

    protected abstract T fallback(@NonNull AdvancedChatEvent<M> event);

    @Override
    public @NonNull T visit(@NonNull Connection<M> event) {
        return fallback(event);
    }

    @Override
    public @NonNull T visit(@NonNull Disconnection<M> event) {
        return fallback(event);
    }

    @Override
    public @NonNull T visit(@NonNull PostedMessage<M> event) {
        return fallback(event);
    }

    @Override
    public @NonNull T visit(@NonNull ReceivedMessage<M> event) {
        return fallback(event);
    }

    @Override
    public @NonNull T visit(@NonNull Error<M> event) {
        return fallback(event);
    }
}
