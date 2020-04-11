package perococco.bot.chat.core;

import bot.chat.core.*;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;

import java.util.function.Function;

/**
 * @author perococco
 **/
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PerococcoReconnectingChatClient extends ChatClientBase implements ChatClient, ChatClientListener {

    private final Function<ChatClientListener,ReconnectionManager> reconnectionManagerFactory;

    private final ChatBridge bridge = new ChatBridge();

    private Thread thread = null;

    @Override
    @Synchronized
    public @NonNull Chat connect() {
        if (thread == null) {
            this.thread = new Thread(reconnectionManagerFactory.apply(this));
            this.thread.setDaemon(true);
            this.thread.setName("Reconnecting Chat Thread");
            this.thread.start();
        }
        return bridge;
    }

    @Override
    @Synchronized
    public void disconnect() {
        if (thread != null) {
            thread.interrupt();
        }
        thread = null;
    }

    @Override
    @Synchronized
    public boolean isConnected() {
        return thread != null && !thread.isInterrupted() && bridge.isConnected();
    }

    @Override
    public void onConnection(@NonNull Chat chat) {
        bridge.setProxy(chat);
        warnListenerOnConnection(bridge);
    }

    @Override
    public void onDisconnection() {
        warnListenerOnDisconnection();
    }

    @NonNull
    public static ReconnectingChatClientFactory provider() {
        return ReconnectingChatClientFactory.with(
                (c,p) -> new PerococcoReconnectingChatClient(ReconnectionManager.factory(c,p))
        );
    }

}
