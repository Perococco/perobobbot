package bot.chat.advanced;

import lombok.NonNull;

import java.time.Duration;

public interface AdvancedChat<M> extends AdvancedChatIO<M> {

    void start();

    void requestStop();

    @NonNull
    Duration timeout();

    void timeout(@NonNull Duration duration);

}
