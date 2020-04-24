package bot.chat.advanced.event;

import lombok.NonNull;

public interface AdvancedChatEventVisitor<M> {

    void visit(@NonNull Connection<M> event);

    void visit(@NonNull Disconnection<M> event);

    void visit(@NonNull PostedMessage<M> event);

    void visit(@NonNull ReceivedMessages<M> event);

    void visit(@NonNull Error<M> event);
}
