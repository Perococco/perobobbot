package perobobbot.chat.core.event;

import lombok.NonNull;

import java.util.function.Consumer;

public interface ChatEventVisitor {

    @NonNull
    default Consumer<ChatEvent> toFunction() {
        return e -> e.accept(this);
    }

    void visit(@NonNull Connection event);

    void visit(@NonNull Disconnection event);

    void visit(@NonNull PostedMessage event);

    void visit(@NonNull ReceivedMessage event);

    void visit(@NonNull Error event);
}
