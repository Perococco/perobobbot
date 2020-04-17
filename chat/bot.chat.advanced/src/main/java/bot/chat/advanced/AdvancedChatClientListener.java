package bot.chat.advanced;

import lombok.NonNull;

import java.util.function.Consumer;

/**
 * @author perococco
 **/
public interface AdvancedChatClientListener<M> {

    void onConnection(@NonNull AdvancedChat<M> chat);

    void onDisconnection();


    @NonNull
    static <M> AdvancedChatClientListener<M> with(@NonNull Consumer<AdvancedChat<M>> onConnection, @NonNull Runnable onDisconnection) {
        return new AdvancedChatClientListener<>() {
            @Override
            public void onConnection(@NonNull AdvancedChat<M> chat) {
                onConnection.accept(chat);
            }

            @Override
            public void onDisconnection() {
                onDisconnection.run();
            }
        };
    }
}
