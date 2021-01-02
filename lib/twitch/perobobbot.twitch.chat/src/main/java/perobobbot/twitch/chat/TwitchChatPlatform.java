package perobobbot.twitch.chat;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import perobobbot.chat.core.ChatConnection;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.lang.*;
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
    public @NonNull CompletionStage<? extends ChatConnection> connect(@NonNull Bot bot) {
        final var connectionInfo = bot.extractConnectionInfo(Platform.TWITCH);

        return connections.computeIfAbsent(connectionInfo.getNick(), n -> new Connector(connectionInfo).connect())
                          .checkIsForBot(connectionInfo)
                          .getConnection();
    }


    @Override
    @Synchronized
    public void dispose() {
        connections.values().forEach(c -> c.getConnection().whenComplete((r, t) -> {
            if (r != null) {
                r.requestStop();
            }
        }));
        connections.clear();
    }

    @Synchronized
    private void removeConnection(@NonNull String nick) {
        connections.remove(nick);
    }

    @Override
    @Synchronized
    public @NonNull Optional<CompletionStage<? extends ChatConnection>> findConnection(@NonNull ConnectionInfo connectionInfo) {
        return Optional.ofNullable(connections.get(connectionInfo))
                       .map(ConnectionData::getConnection);
    }

    @Override
    public @NonNull Subscription addMessageListener(@NonNull MessageListener listener) {
        return listeners.addListener(TwitchChatListener.wrap(listener));
    }

    private final class ConnectionData {

        @Getter
        private final @NonNull ConnectionInfo connectionInfo;

        @Getter
        private final @NonNull CompletionStage<TwitchChatConnection> connection;

        public ConnectionData(@NonNull ConnectionInfo connectionInfo) {
            final var nick = connectionInfo.getNick();
            this.connectionInfo = connectionInfo;
            this.connection = new TwitchChatConnection(connectionInfo, listeners)
                    .start()
                    .whenComplete((result, error) -> {
                        if (error != null) {
                            LOG.warn("Could not connect to twitch chat for nick {}", nick, error);
                            removeConnection(nick);
                        }
                    });
        }

        public boolean isForBot(ConnectionInfo connectionInfo) {
            return this.connectionInfo.equals(connectionInfo);
        }

        public ConnectionData checkIsForBot(ConnectionInfo connectionInfo) {
            if (this.connectionInfo.equals(connectionInfo)) {
                return this;
            }
            throw new PerobobbotException("Multiple bots try to connect with the same nickname : '" + connectionInfo.getNick() + "'");
        }
    }

    @RequiredArgsConstructor
    private class Connector {

        private final ConnectionInfo connectionInfo;

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
            return new ConnectionData(connectionInfo);
        }

        private @NonNull ConnectionData checkExistingConnection() {
            assert connectionData != null;
            if (connectionData.isForBot(connectionInfo)) {
                return connectionData;
            }
            throw new PerobobbotException("Invalid authentication for chat connection");
        }

        private void retrieveConnectionData() {
            this.connectionData = connections.get(connectionInfo.getNick());
        }

    }
}

