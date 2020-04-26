package bot.chat.advanced.event;

import lombok.NonNull;

import java.util.Optional;

public class ReceivedMessageExtractor<M> extends AdvancedChatEventAdapter<M, Optional<ReceivedMessage<M>>> {

    @Override
    protected Optional<ReceivedMessage<M>> fallback(@NonNull AdvancedChatEvent<M> event) {
        return Optional.empty();
    }

    @NonNull
    @Override
    public Optional<ReceivedMessage<M>> visit(@NonNull ReceivedMessage<M> event) {
        return Optional.of(event);
    }
}
