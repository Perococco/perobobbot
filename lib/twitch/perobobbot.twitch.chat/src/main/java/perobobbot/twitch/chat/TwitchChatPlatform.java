package perobobbot.twitch.chat;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import perobobbot.chat.core.ChatAuthentication;
import perobobbot.chat.core.ChatConnection;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.lang.*;
import perobobbot.twitch.chat.TwitchChatListener;
import perococco.perobobbot.twitch.chat.TwitchChatConnection;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

@Log4j2
public class TwitchChatPlatform implements ChatPlatform {

    private final Listeners<TwitchChatListener> listeners = new Listeners<>();

    private final Map<String, ConnectionData> connections = new HashMap<>();

    @Override
    public @NonNull Platform getPlatform() {
        return Platform.TWITCH;
    }

    @Override
    @Synchronized
    public @NonNull CompletionStage<? extends ChatConnection> connect(@NonNull ChatAuthentication authentication) {
        return connections.computeIfAbsent(authentication.getNick(), n -> new Connector(authentication).connect())
                          .getConnection();
    }

    @Synchronized
    private void removeConnection(@NonNull String nick) {
        connections.remove(nick);
    }

    @Override
    @Synchronized
    public @NonNull Optional<CompletionStage<? extends ChatConnection>> findConnection(@NonNull String nick) {
        return Optional.ofNullable(connections.get(nick)).map(ConnectionData::getConnection);
    }

    @Override
    public @NonNull Subscription addMessageListener(@NonNull MessageListener listener) {
        return listeners.addListener(TwitchChatListener.wrap(listener));
    }

    private final class ConnectionData {

        @Getter
        private final @NonNull ChatAuthentication authentication;

        @Getter
        private final @NonNull CompletionStage<TwitchChatConnection> connection;

        public ConnectionData(@NonNull ChatAuthentication authentication) {
            this.authentication = authentication;
            this.connection = new TwitchChatConnection(authentication, listeners)
                    .start()
                    .whenComplete((result, error) -> {
                        if (error != null) {
                            LOG.warn("Could not connect to twitch chat for nick {}", authentication.getNick(), error);
                            removeConnection(authentication.getNick());
                        }
                    });
        }

    }

    @RequiredArgsConstructor
    private class Connector {

        private final ChatAuthentication authentication;

        private ConnectionData connectionData = null;

        public ConnectionData connect() {
            this.retrieveConnectionData();
            if (connectionData == null) {
                return this.createNewConnection();
            } else {
                return this.checkExistingConnection();
            }
        }

        private @NonNull ConnectionData createNewConnection() {
            return new ConnectionData(authentication);
        }

        private @NonNull ConnectionData checkExistingConnection() {
            assert connectionData != null;
            if (connectionData.getAuthentication().equals(authentication)) {
                return connectionData;
            }
            throw new PerobobbotException("Invalid authentication for chat connection");
        }

        private void retrieveConnectionData() {
            this.connectionData = connections.get(authentication.getNick());
        }

    }
}
