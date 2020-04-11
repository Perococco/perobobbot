package bot.chat.core;

import lombok.NonNull;

import java.util.function.Consumer;

/**
 * @author perococco
 **/
public interface ChatClientListener {

    void onConnection(@NonNull Chat chat);

    void onDisconnection();


    @NonNull
    static ChatClientListener with(@NonNull Consumer<Chat> onConnection, @NonNull Runnable onDisconnection) {
        return new ChatClientListener() {
            @Override
            public void onConnection(@NonNull Chat chat) {
                onConnection.accept(chat);
            }

            @Override
            public void onDisconnection() {
                onDisconnection.run();
            }
        };
    }
}
