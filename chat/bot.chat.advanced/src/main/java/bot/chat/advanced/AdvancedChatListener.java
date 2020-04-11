package bot.chat.advanced;

import lombok.NonNull;

public interface AdvancedChatListener {

    void onReceivedMessage(@NonNull Message receivedMessage);

    void onPostMessage(@NonNull Message postMessage);

    void onError(@NonNull Throwable throwable);


}
