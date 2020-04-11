package perococco.bot.chat.core;

import bot.chat.core.Chat;
import bot.chat.core.ChatClient;
import bot.chat.core.ChatClientListener;
import bot.chat.core.ReconnectionPolicy;
import bot.common.lang.SmartLock;
import bot.common.lang.Subscription;
import bot.common.lang.ThrowableTool;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.time.Duration;
import java.util.concurrent.locks.Condition;
import java.util.function.Function;

/**
 * @author perococco
 **/
@Log4j2
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReconnectionManager implements Runnable {

    public static final Marker CHAT_MARKER = MarkerManager.getMarker("CHAT");

    @NonNull
    public static Function<ChatClientListener,ReconnectionManager> factory(@NonNull ChatClient chatClient, @NonNull ReconnectionPolicy policy) {
        return listener -> new ReconnectionManager(chatClient,policy,listener);
    }

    @NonNull
    private final ChatClient chatClient;

    @NonNull
    private final ReconnectionPolicy policy;

    @NonNull
    private final ChatClientListener chatClientListener;

    private final SmartLock lock = SmartLock.reentrant();

    private final Condition disconnection = lock.newCondition();

    private Subscription subscription = Subscription.NONE;

    public void run() {
        try {
            this.initializeLoop();
            this.connect();
            while (!Thread.currentThread().isInterrupted()) {
                this.waitForDisconnection();
                this.performReconnection();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            this.finalizeLoop();
        }

    }

    private void connect(){
        chatClient.connect();
    }

    private void waitForDisconnection() throws InterruptedException {
        lock.await(disconnection);
    }

    @SuppressWarnings("BusyWait")
    private void performReconnection() throws InterruptedException {
        int attemptIndex = 0;
        while (policy.shouldReconnect(attemptIndex)) {
            ++attemptIndex;
            final Duration delay = policy.delayBeforeNextAttempt(attemptIndex);
            if (!delay.isNegative() && !delay.isZero()) {
                Thread.sleep(delay.toMillis());
            }

            try {
                connect();
            } catch (Exception e) {
                LOG.warn(CHAT_MARKER,"Fail to reconnect to chat",e);
                if (ThrowableTool.isCausedByAnInterruption(e)) {
                    throw new InterruptedException();
                }
            }
        }
    }


    private void initializeLoop() {
        this.addOwnChatListenerToChatClient();
    }

    private void addOwnChatListenerToChatClient() {
        this.subscription = chatClient.addChatClientListener(ChatClientListener.with(this::onConnection,this::onDisconnection));
    }

    private void finalizeLoop() {
        this.removeOwnChatListenerFromChatClient();
    }

    private void removeOwnChatListenerFromChatClient() {
        this.subscription.unsubscribe();
        this.subscription = Subscription.NONE;
    }

    private void onConnection(@NonNull Chat chat) {
        this.chatClientListener.onConnection(chat);
    }

    private void onDisconnection() {
        this.chatClientListener.onDisconnection();
        lock.runLocked(disconnection::signalAll);
    }
}
