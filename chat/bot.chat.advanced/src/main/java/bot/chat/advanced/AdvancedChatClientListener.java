package bot.chat.advanced;

import lombok.NonNull;

import java.util.function.Consumer;

/**
 * @author perococco
 **/
public interface AdvancedChatClientListener {

    void onConnection(@NonNull AdvancedChat chat);

    void onDisconnection();


    @NonNull
    static <C> AdvancedChatClientListener with(@NonNull Consumer<AdvancedChat> onConnection, @NonNull Runnable onDisconnection) {
        return new AdvancedChatClientListener() {
            @Override
            public void onConnection(@NonNull AdvancedChat chat) {
                onConnection.accept(chat);
            }

            @Override
            public void onDisconnection() {
                onDisconnection.run();
            }
        };
    }
}
