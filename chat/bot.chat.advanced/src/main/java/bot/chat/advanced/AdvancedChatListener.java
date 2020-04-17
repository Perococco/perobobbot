package bot.chat.advanced;

import lombok.NonNull;

public interface AdvancedChatListener<M> {

    void onReceivedMessage(@NonNull M receivedMessage);

    void onPostMessage(@NonNull Message postMessage);

    void onError(@NonNull Throwable throwable);


}
