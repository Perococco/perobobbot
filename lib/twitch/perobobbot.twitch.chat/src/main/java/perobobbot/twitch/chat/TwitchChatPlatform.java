package perobobbot.twitch.chat;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
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
@RequiredArgsConstructor
public class TwitchChatPlatform implements ChatPlatform {

    private final @NonNull Instants instants;

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
    public @NonNull Optional<CompletionStage<? extends ChatConnection>> findConnection(@NonNull ChatConnectionInfo chatConnectionInfo) {
        return Optional.ofNullable(connections.get(chatConnectionInfo.getNick()))
                       .map(ConnectionData::getConnection);
    }

    @Override
    public @NonNull Subscription addMessageListener(@NonNull MessageListener listener) {
        return listeners.addListener(TwitchChatListener.wrap(listener));
    }

    private final class ConnectionData {

        @Getter
        private final @NonNull ChatConnectionInfo chatConnectionInfo;

        @Getter
        private final @NonNull CompletionStage<TwitchChatConnection> connection;

        public ConnectionData(@NonNull ChatConnectionInfo chatConnectionInfo) {
            final var nick = chatConnectionInfo.getNick();
            this.chatConnectionInfo = chatConnectionInfo;
            this.connection = new TwitchChatConnection(chatConnectionInfo, listeners, instants)
                    .start()
                    .whenComplete((result, error) -> {
                        if (error != null) {
                            LOG.warn("Could not connect to twitch chat for nick {} : {}", nick, error.getMessage());
                            LOG.debug(error);
                            removeConnection(nick);
                        }
                    });
        }

        public boolean isForBot(ChatConnectionInfo chatConnectionInfo) {
            return this.chatConnectionInfo.equals(chatConnectionInfo);
        }

        public ConnectionData checkIsForBot(ChatConnectionInfo chatConnectionInfo) {
            if (this.chatConnectionInfo.equals(chatConnectionInfo)) {
                return this;
            }
            throw new PerobobbotException("Multiple bots try to connect with the same nickname : '" + chatConnectionInfo.getNick() + "'");
        }
    }

    @RequiredArgsConstructor
    private class Connector {

        private final ChatConnectionInfo chatConnectionInfo;

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
            return new ConnectionData(chatConnectionInfo);
        }

        private @NonNull ConnectionData checkExistingConnection() {
            assert connectionData != null;
            if (connectionData.isForBot(chatConnectionInfo)) {
                return connectionData;
            }
            throw new PerobobbotException("Invalid authentication for chat connection");
        }

        private void retrieveConnectionData() {
            this.connectionData = connections.get(chatConnectionInfo.getNick());
        }

    }
}

