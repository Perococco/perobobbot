package perobobbot.discord.gateway.impl.state.connection;

import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import perobobbot.discord.gateway.impl.MessageMapper;
import perobobbot.discord.gateway.impl.message.SentGatewayEvent;
import perobobbot.lang.MessageSaver;
import perobobbot.lang.ThrowableTool;
import perobobot.discord.gateway.api.Markers;

import javax.websocket.*;
import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
public class Connection implements Closeable {


    public static final boolean SAVE_MESSAGE_ON_ERROR = Boolean.getBoolean("save-discord-message-on-error");

    private final @NonNull URI gatewayURI;
    private final @NonNull MessageMapper messageMapper;
    private final MessageSaver messageSaver = new MessageSaver("discord_event_",".json");

    private AtomicReference<Session> sessionHandler = new AtomicReference<>();
    private final BlockingDeque<ConnectionEvent> queue;

    public Connection(@NonNull URI gatewayURI, @NonNull MessageMapper messageMapper) {
        this(gatewayURI,messageMapper, Integer.MAX_VALUE);
    }

    public Connection(@NonNull URI gatewayURI, @NonNull MessageMapper messageMapper, int queueCapacity) {
        this.gatewayURI = gatewayURI;
        this.messageMapper = messageMapper;
        this.queue = new LinkedBlockingDeque<>(queueCapacity);
    }

    @Override
    public void close() throws IOException {
        disconnect();
    }

    public @NonNull ConnectionEvent takeEvent() throws InterruptedException {
        return this.queue.take();
    }

    public @NonNull Optional<ConnectionEvent> pollEvent(long timeout, TimeUnit timeUnit) throws InterruptedException {
        return Optional.ofNullable(this.queue.poll(timeout, timeUnit));
    }

    public @NonNull Optional<ConnectionEvent> pollEvent() {
        return Optional.ofNullable(this.queue.poll());
    }

    @Synchronized
    public void connect() {
        if (this.sessionHandler.get() != null) {
            return;
        }
        final var webSocketContainer = ContainerProvider.getWebSocketContainer();
        try {
            final var session = webSocketContainer.connectToServer(new ConnectionEndPoint(), gatewayURI);
            sessionHandler.set(session);
        } catch (Exception e) {
            throw new ConnectionError(e);
        }
    }

    @Synchronized
    public void disconnect() {
        try {
            final var session = this.sessionHandler.getAndSet(null);
            if (session != null) {
                session.close();
            }
        } catch (Exception e) {
            throw new ConnectionError(e);
        }
    }

    public void send(@NonNull SentGatewayEvent gatewayEvent) {
        final var message = messageMapper.map(gatewayEvent);
        try {
            this.getSession().getBasicRemote().sendText(message);
        } catch (IOException e) {
            throw new ConnectionError("Could not send text to Discord",e);
        }
    }

    private Session getSession() {
        final var session = this.sessionHandler.get();
        if (session == null) {
            throw new ConnectionError("Cannot send a message, not session");
        }
        return session;
    }

    public void sendHeartbeat(@NonNull Integer seqNumber) {
        final var message = messageMapper.mapHeartbeat(seqNumber);
        try {
            this.getSession().getBasicRemote().sendText(message);
        } catch (IOException e) {
            throw new ConnectionError("Could not send text to Discord",e);
        }
    }

    private class ConnectionEndPoint extends Endpoint implements MessageHandler.Whole<String> {

        @Override
        public void onOpen(Session session, EndpointConfig config) {
            session.addMessageHandler(this);
            offerEvent(new ConnectionEvent.Open(config));
        }

        @Override
        public void onClose(Session session, CloseReason closeReason) {
            offerEvent(new ConnectionEvent.Close(closeReason));
            session.removeMessageHandler(this);
            disconnect();
        }

        @Override
        public void onError(Session session, Throwable thr) {
            offerEvent(new ConnectionEvent.Error(thr));
        }

        @Override
        public void onMessage(String rawMessage)
        {
            LOG.debug(Markers.GATEWAY, "Receive message {}",rawMessage);
            messageSaver.saveMessage(rawMessage);
            try {
                final var message = messageMapper.map(rawMessage);
                offerEvent(new ConnectionEvent.Message(message));
            } catch (Throwable t) {
                ThrowableTool.interruptThreadIfCausedByInterruption(t);
                LOG.warn(Markers.GATEWAY, "Error when handling message '{}'",rawMessage);
            }
        }

        private void offerEvent(@NonNull ConnectionEvent connectionEvent) {
            System.out.println("EVENT "+connectionEvent);
            final var success = queue.offer(connectionEvent);
            if (!success) {
                LOG.warn("Could not offer event {}", connectionEvent);
            }
        }
    }

    private void saveMessage(String rawMessage) {

    }
}
