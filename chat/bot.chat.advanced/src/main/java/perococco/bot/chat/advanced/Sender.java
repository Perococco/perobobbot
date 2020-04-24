package perococco.bot.chat.advanced;

import bot.chat.advanced.AdvancedChat;
import bot.chat.advanced.AdvancedChatListener;
import bot.chat.advanced.event.Error;
import bot.chat.advanced.event.PostedMessage;
import bot.chat.core.Chat;
import bot.chat.core.ChatListener;
import bot.chat.core.ChatNotConnected;
import bot.common.lang.Listeners;
import bot.common.lang.Looper;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public class Sender<M> extends Looper {

    @NonNull
    private final Chat chat;

    @NonNull
    private final Listeners<AdvancedChatListener<M>> listeners;

    @NonNull
    private final BlockingDeque<RequestPostData<?,M>> requestPostDataQueue;

    @NonNull
    private final BlockingDeque<PostData<?,M>> postQueue = new LinkedBlockingDeque<>();

    private final Timer timer = new Timer("Request timeout", true);

    @NonNull
    @Getter
    @Setter
    private Duration timeout = Duration.ofMinutes(1);

    public <A> CompletionStage<A> send(@NonNull PostData<A,M> postData) {
        if (isRunning()) {
            postQueue.add(postData);
            return postData.completionStage();
        } else {
            return CompletableFuture.failedFuture(new ChatNotConnected());
        }
    }

    @Override
    protected @NonNull IterationCommand performOneIteration() throws Exception {
        final PostData<?,M> postData = postQueue.takeFirst();

        try {
            postData.asRequestPostData().ifPresent(this::handleRequestPostData);
            final Instant dispatchingTime = Instant.now();
            this.postMessageToChat(postData);
            this.performActionsAfterSuccessfulPost(postData,dispatchingTime);
        } catch (Throwable t) {
            this.performActionsAfterFailedPost(postData,t);
            throw t;
        }

        return IterationCommand.CONTINUE;
    }

    private void handleRequestPostData(@NonNull RequestPostData<?,M> requestPostData) {
        this.addRequestPostDataToQueue(requestPostData);
        this.addTimeoutTimerForRequest(requestPostData);
    }

    private void addRequestPostDataToQueue(@NonNull RequestPostData<?,M> requestPostData) {
        requestPostDataQueue.offerLast(requestPostData);
    }

    protected void addTimeoutTimerForRequest(@NonNull RequestPostData<?,M> requestPostData) {
        final Duration timeout = this.timeout;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                requestPostData.onRequestTimeout(timeout);
            }
        },timeout.toMillis());
    }

    private void postMessageToChat(@NonNull PostData<?,M> postData) {
        chat.postMessage(postData.messagePayload());
    }

    private void performActionsAfterSuccessfulPost(@NonNull PostData<?,M> postData, @NonNull Instant dispatchingTime) {
        final PostedMessage<M> postedMessage = new PostedMessage<>(dispatchingTime, postData.message());
        listeners.warnListeners(AdvancedChatListener::onChatEvent, postedMessage);
        postData.onMessagePosted(dispatchingTime);
    }

    private void performActionsAfterFailedPost(@NonNull PostData<?,M> postData, @NonNull Throwable t) {
        final Error<M> error = new Error<>(t);
        listeners.warnListeners(AdvancedChatListener::onChatEvent,error);
        postData.onMessagePostFailure(t);
    }

}
