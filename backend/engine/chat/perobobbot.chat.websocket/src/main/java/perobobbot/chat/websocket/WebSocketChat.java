package perobobbot.chat.websocket;

import perobobbot.chat.core.*;
import perobobbot.chat.core.event.Connection;
import perobobbot.chat.core.event.Disconnection;
import perobobbot.chat.core.event.Error;
import perobobbot.chat.core.event.ReceivedMessage;
import perobobbot.common.lang.Looper;
import perobobbot.common.lang.SmartLock;
import perobobbot.common.lang.ThrowableTool;
import perobobbot.common.lang.WaitStrategy;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;

/**
 * @author perococco
 **/
@Log4j2
public class WebSocketChat extends ChatIOBase implements Chat {

    private final Looper looper;

    private final AtomicReference<Session> sessionReference = new AtomicReference<>(null);

    private final SmartLock lock = SmartLock.reentrant();

    private final Condition disconnection = lock.newCondition();

    public WebSocketChat(@NonNull URI uri) {
        this(uri, ReconnectionPolicy.NO_RECONNECTION);
    }

    public WebSocketChat(@NonNull URI uri, @NonNull ReconnectionPolicy policy) {
        this(uri, policy, WaitStrategy.create());
    }

    public WebSocketChat(@NonNull URI uri, @NonNull ReconnectionPolicy policy, @NonNull WaitStrategy waitStrategy) {
        this(ContainerProvider.getWebSocketContainer(), uri, policy, waitStrategy);
    }

    public WebSocketChat(
            @NonNull WebSocketContainer webSocketContainer,
            @NonNull URI uri,
            @NonNull ReconnectionPolicy policy,
            @NonNull WaitStrategy waitStrategy) {
        this.looper = new ChatLooper(webSocketContainer, uri, policy, waitStrategy);
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
            warnListeners(new ReceivedMessage(Instant.now(), message));
        }
    }


}
