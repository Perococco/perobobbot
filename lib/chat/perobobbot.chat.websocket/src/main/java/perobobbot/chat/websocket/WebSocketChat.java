package perobobbot.chat.websocket;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.chat.core.*;
import perobobbot.chat.core.event.Connection;
import perobobbot.chat.core.event.Disconnection;
import perobobbot.chat.core.event.Error;
import perobobbot.chat.core.event.ReceivedMessage;
import perobobbot.lang.*;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;

/**
 * @author perococco
 **/
@Log4j2
public class WebSocketChat extends ChatIOBase implements Chat {

    private final Looper looper;

    private final @NonNull Instants instants;

    private final AtomicReference<Session> sessionReference = new AtomicReference<>(null);

    private final SmartLock lock = SmartLock.reentrant();

    private final Condition disconnection = lock.newCondition();

    public WebSocketChat(@NonNull URI uri, @NonNull Instants instants) {
        this(uri, ReconnectionPolicy.NO_RECONNECTION, instants);
    }

    public WebSocketChat(@NonNull URI uri, @NonNull ReconnectionPolicy policy, @NonNull Instants instants) {
        this(uri, policy, WaitStrategy.create(), instants);
    }

    public WebSocketChat(@NonNull URI uri, @NonNull ReconnectionPolicy policy, @NonNull WaitStrategy waitStrategy, @NonNull Instants instants) {
        this(ContainerProvider.getWebSocketContainer(), uri, policy, waitStrategy, instants);
    }

    public WebSocketChat(
            @NonNull WebSocketContainer webSocketContainer,
            @NonNull URI uri,
            @NonNull ReconnectionPolicy policy,
            @NonNull WaitStrategy waitStrategy,
            @NonNull Instants instants) {
        this.looper = new ChatLooper(webSocketContainer, uri, policy, waitStrategy);
        this.instants = instants;
    }

    @Override
    public void start() {
        looper.start();
    }

    @Override
    public boolean isRunning() {
        return looper.isRunning();
    }

    @Override
    public void requestStop() {
        looper.requestStop();
    }

    @Override
    public void postMessage(@NonNull String message) {
        final Session session = sessionReference.get();
        if (session == null) {
            throw new ChatNotConnected();
        }
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            throw new MessagePostingFailure(message, e);
        }
    }

    @RequiredArgsConstructor
    private class ChatLooper extends Looper {

        @NonNull
        private final WebSocketContainer webSocketContainer;

        @NonNull
        private final URI uri;

        @NonNull
        private final ReconnectionPolicy reconnectionPolicy;

        @NonNull
        private final WaitStrategy waitStrategy;

        @Override
        protected void beforeLooping() {
            super.beforeLooping();
            this.connect();
        }

        private void connect() {
            try {
                webSocketContainer.connectToServer(new ChatEndPoint(), uri);
            } catch (DeploymentException | IOException e) {
                throw new ChatConnectionFailure("Connection failed", e);
            }
        }

        @Override
        protected @NonNull IterationCommand performOneIteration() throws Exception {
            this.waitForDisconnection();
            int attemptIndex = 0;
            boolean connected = false;

            while (
                    !connected
                    && reconnectionPolicy.shouldReconnect(attemptIndex)
                    && !Thread.currentThread().isInterrupted()
            ) {
                attemptIndex++;
                LOG.warn("Try reconnection : attempt #{} ", attemptIndex);
                final Duration duration = reconnectionPolicy.delayBeforeNextAttempt(attemptIndex);
                waitStrategy.waitFor(duration);
                connected = tryToConnect();
            }
            LOG.warn("Reconnection successful.");
            return IterationCommand.CONTINUE;
        }

        private void waitForDisconnection() throws InterruptedException {
            lock.await(disconnection);
        }

        private boolean tryToConnect() {
            try {
                connect();
                return true;
            } catch (Exception e) {
                ThrowableTool.interruptThreadIfCausedByInterruption(e);
                LOG.warn("Connection failed", e);
            }
            return false;
        }

        @Override
        protected void afterLooping() {
            super.afterLooping();
            Optional.ofNullable(sessionReference.get()).ifPresent(s -> {
                try {
                    s.close();
                } catch (IOException e) {
                    ThrowableTool.interruptThreadIfCausedByInterruption(e);
                    LOG.warn("Error while closing websocket", e);
                }
            });
        }

    }


    private class ChatEndPoint extends Endpoint implements MessageHandler.Whole<String> {

        @Override
        public void onClose(Session session, CloseReason closeReason) {
            super.onClose(session, closeReason);
            session.removeMessageHandler(this);
            sessionReference.set(null);
            warnListeners(Disconnection.create());
            lock.runLocked(disconnection::signalAll);
        }

        @Override
        public void onError(Session session, Throwable thr) {
            super.onError(session, thr);
            warnListeners(Error.with(thr));
        }

        @Override
        public void onOpen(Session session, EndpointConfig config) {
            session.addMessageHandler(this);
            sessionReference.set(session);
            warnListeners(Connection.create());
        }

        @Override
        public void onMessage(String message) {
            warnListeners(new ReceivedMessage(instants.now(), message));
        }
    }


}
