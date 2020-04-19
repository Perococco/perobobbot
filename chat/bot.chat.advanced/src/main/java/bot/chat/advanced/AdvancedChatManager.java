package bot.chat.advanced;

import lombok.NonNull;

import java.time.Duration;

public interface AdvancedChatManager<M> extends AdvancedChat<M> {

    void start();

    void requestStop();

    @NonNull
    Duration timeout();

    void timeout(@NonNull Duration duration);

}
