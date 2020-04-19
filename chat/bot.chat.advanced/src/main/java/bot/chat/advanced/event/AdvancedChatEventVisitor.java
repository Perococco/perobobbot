package bot.chat.advanced.event;

import lombok.NonNull;

public interface AdvancedChatEventVisitor<M> {

    @NonNull
    void visit(@NonNull Connection<M> event);

    @NonNull
    void visit(@NonNull Disconnection<M> event);

    @NonNull
    void visit(@NonNull PostedMessage<M> event);

    @NonNull
    void visit(@NonNull ReceivedMessages<M> event);

    @NonNull
    void visit(@NonNull Error<M> event);
}
