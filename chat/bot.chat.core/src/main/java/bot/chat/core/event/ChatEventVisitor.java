package bot.chat.core.event;

import lombok.NonNull;

import java.util.function.Consumer;
import java.util.function.Function;

public interface ChatEventVisitor {

    @NonNull
    default Consumer<ChatEvent> toFunction() {
        return e -> e.accept(this);
    }

    void visit(@NonNull Connection event);

    @NonNull
    void visit(@NonNull Disconnection event);

    @NonNull
    void visit(@NonNull PostedMessage event);

    @NonNull
    void visit(@NonNull ReceivedMessage event);

    @NonNull
    void visit(@NonNull Error event);
}
