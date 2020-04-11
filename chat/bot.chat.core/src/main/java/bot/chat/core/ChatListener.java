package bot.chat.core;

import lombok.NonNull;

/**
 * @author perococco
 **/
public interface ChatListener {

    void onReceivedMessage(@NonNull String receivedMessage);

    void onPostMessage(@NonNull String postMessage);

    void onError(@NonNull Throwable throwable);

}
