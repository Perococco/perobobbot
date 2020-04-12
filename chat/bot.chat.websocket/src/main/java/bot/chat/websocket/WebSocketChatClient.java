package bot.chat.websocket;

import bot.chat.core.Chat;
import bot.chat.core.ChatClientBase;
import bot.chat.core.ChatConnectionFailure;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perococco.bot.chat.websocket.WebSocketChat;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public class WebSocketChatClient extends ChatClientBase {

    @NonNull
    private final WebSocketContainer webSocketContainer;

    @NonNull
    private final URI uri;

    private WebSocketChat chat = null;

    public WebSocketChatClient(@NonNull URI uri) {
        this(ContainerProvider.getWebSocketContainer(),uri);
    }

    @Override
    @Synchronized
    public @NonNull Chat connect() {
        if (chat != null) {
            return chat;
        }
        try {
            webSocketContainer.connectToServer(new MyEndPoint(), uri);
            return chat;
        } catch (DeploymentException | IOException e) {
            throw new ChatConnectionFailure("Could not connect to '" + uri + "'", e);
        }

    }

    @Override
    @Synchronized
    public void disconnect() {
        Optional.ofNullable(chat).ifPresent(WebSocketChat::dispose);
        chat = null;
    }

    @Override
    @Synchronized
    public boolean isConnected() {
        return chat != null;
    }

    @Override
    public boolean hasReconnectingProperty() {
        return false;
    }

    private class MyEndPoint extends Endpoint {
        @Override
        public void onClose(Session session, CloseReason closeReason) {
            super.onClose(session, closeReason);
            warnListenerOnDisconnection();
        }

        @Override
        public void onError(Session session, Throwable thr) {
            super.onError(session, thr);
        }

        @Override
        public void onOpen(Session session, EndpointConfig config) {
            chat = new WebSocketChat(session);
            warnListenerOnConnection(chat);
        }
    }


}
