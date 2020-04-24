package bot.chat.websocket;

import bot.chat.core.*;
import bot.chat.core.event.Connection;
import bot.chat.core.event.Disconnection;
import bot.chat.core.event.Error;
import bot.chat.core.event.ReceivedMessage;
import bot.common.lang.Looper;
import bot.common.lang.SmartLock;
import bot.common.lang.ThrowableTool;
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

    public WebSocketChat(@NonNull URI uri, @NonNull ReconnectionPolicy policy) {
        this(ContainerProvider.getWebSocketContainer(),uri, policy);
    }

    public WebSocketChat(@NonNull URI uri) {
        this(ContainerProvider.getWebSocketContainer(),uri, ReconnectionPolicy.NO_RECONNECTION);
    }

    public WebSocketChat(@NonNull WebSocketContainer webSocketContainer, @NonNull URI uri, @NonNull ReconnectionPolicy policy) {
        this.looper = new ChatLooper(webSocketContainer,uri,policy);
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
            throw new MessagePostingFailure(message,e);
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

        @Override
        protected void beforeLooping() {
            super.beforeLooping();
            this.connect();
        }

        @Override
        protected void afterLooping() {
            super.afterLooping();
            Optional.ofNullable(sessionReference.get()).ifPresent(s -> {
                try {
                    s.close();
                } catch (IOException e) {
                    ThrowableTool.interruptThreadIfCausedByInterruption(e);
                    LOG.warn("Error while close websocket",e);
                }
            });
        }

        @Override
        protected @NonNull IterationCommand performOneIteration() throws Exception {
            this.waitForDisconnection();
            int tryIndex = 0;
            while(reconnectionPolicy.shouldReconnect(tryIndex) && !Thread.currentThread().isInterrupted()) {
                tryIndex++;
                LOG.warn("Try reconnection : attempt #{} ",tryIndex);
                final Duration duration = reconnectionPolicy.delayBeforeNextAttempt(tryIndex);
                if (!duration.isNegative() && !duration.isZero()) {
                    //IMPROVE busy wait in loop.
                    Thread.sleep(duration.toMillis());
                }
                try {
                    connect();
                    break;
                } catch (Exception e) {
                    LOG.warn("Connection failed",e);
                }
            }
            return IterationCommand.CONTINUE;
        }

        private void waitForDisconnection() throws InterruptedException {
            lock.await(disconnection);
        }

        private void connect() {
            try {
                webSocketContainer.connectToServer(new ChatEndPoint(), uri);
            } catch (DeploymentException | IOException e) {
                throw new ChatConnectionFailure("Connection failed", e);
            }
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
