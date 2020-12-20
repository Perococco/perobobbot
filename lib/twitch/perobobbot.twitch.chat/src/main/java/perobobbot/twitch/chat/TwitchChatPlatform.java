package perobobbot.twitch.chat;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import perobobbot.lang.NoCredentialForChatConnection;
import perobobbot.lang.Credentials;
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
        final var nickname = bot.getCredentialsNick(Platform.TWITCH);

        return connections.computeIfAbsent(nickname, n -> new Connector(bot).connect())
                   .checkIsForBot(bot)
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
    public @NonNull Optional<CompletionStage<? extends ChatConnection>> findConnection(@NonNull Bot bot) {
        return Optional.ofNullable(connections.get(bot.getCredentialsNick(Platform.TWITCH)))
                       .map(ConnectionData::getConnection);
    }

    @Override
    public @NonNull Subscription addMessageListener(@NonNull MessageListener listener) {
        return listeners.addListener(TwitchChatListener.wrap(listener));
    }

    private final class ConnectionData {

        @Getter
        private final @NonNull Bot bot;

        @Getter
        private final @NonNull CompletionStage<TwitchChatConnection> connection;

        public ConnectionData(@NonNull Bot bot) {
            final var nick = bot.getCredentialsNick(Platform.TWITCH);
            this.bot = bot;
            this.connection = new TwitchChatConnection(bot, listeners)
                    .start()
                    .whenComplete((result, error) -> {
                        if (error != null) {
                            LOG.warn("Could not connect to twitch chat for nick {}", nick, error);
                            removeConnection(nick);
                        }
                    });
        }

        public boolean isForBot(Bot bot) {
            return this.bot.equals(bot);
        }

        public ConnectionData checkIsForBot(Bot bot) {
            if (this.bot.equals(bot)) {
                return this;
            }
            throw new PerobobbotException("Multiple bots try to connect with the same nickname : '"+bot.getCredentialsNick(Platform.TWITCH)+"'");
        }
    }

    @RequiredArgsConstructor
    private class Connector {

        private final Bot bot;

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
            return new ConnectionData(bot);
        }

        private @NonNull ConnectionData checkExistingConnection() {
            assert connectionData != null;
            if (connectionData.isForBot(bot)) {
                return connectionData;
            }
            throw new PerobobbotException("Invalid authentication for chat connection");
        }

        private void retrieveConnectionData() {
            this.connectionData = connections.get(bot.getCredentialsNick(Platform.TWITCH));
        }

    }
}

