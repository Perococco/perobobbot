package perococco.bot.chat.advanced;

import bot.chat.advanced.AdvancedChatListener;
import bot.chat.advanced.MessageConverter;
import bot.chat.core.Chat;
import bot.chat.core.ChatListener;
import bot.common.lang.Listeners;
import bot.common.lang.Looper;
import bot.common.lang.Subscription;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Predicate;

/**
 * @author perococco
 **/
@Log4j2
@RequiredArgsConstructor
public class Receiver<M> extends Looper {

    @NonNull
    private final Chat chat;

    @NonNull
    private final Listeners<AdvancedChatListener<M>> listeners;

    @NonNull
    private final BlockingDeque<RequestPostData<?,M>> requestPostData;

    @NonNull
    private final MessageConverter<M> messageFactory;

    private final Predicate<? super M> shouldPerformMatching;

    @NonNull
    private final BlockingDeque<Reception<M>> incomingMessages = new LinkedBlockingDeque<>();

    @NonNull
    private final List<RequestPostData<?,M>> pending = new LinkedList<>();

    private Subscription subscription = Subscription.NONE;


    @Override
    protected void beforeLooping() {
        subscription = Subscription.NONE;
        subscription = chat.addChatListener(new Listener());
    }

    @Override
    protected @NonNull IterationCommand performOneIteration() throws Exception {
        final Reception<M> reception = incomingMessages.takeFirst();
        listeners.warnListeners(AdvancedChatListener::onReceivedMessage,reception.message);

        if (shouldPerformMatching.test(reception.message)) {
            final boolean messageHandled = this.performRendezvousWithRequests(reception);
            if (!messageHandled) {
                LOG.debug("Message without request : {}", reception);
            }
        }

        return IterationCommand.CONTINUE;
    }

    private boolean performRendezvousWithRequests(@NonNull Reception<M> incomingMessage) {
        this.preparePendingRequests();
        for (RequestPostData<?,M> postData : pending) {
            if (postData.tryToCompleteWith(incomingMessage.message(), incomingMessage.time())) {
                return true;
            }
        }
        return false;
    }

    private void preparePendingRequests() {
        requestPostData.drainTo(pending);
        pending.removeIf(RequestPostData::isCompleted);
    }

    @Override
    protected void afterLooping() {
        subscription.unsubscribe();
        subscription = Subscription.NONE;
    }

    private class Listener implements ChatListener {

        @Override
        public void onReceivedMessage(@NonNull String receivedMessage) {
            final Instant receptionTime = Instant.now();
            messageFactory.convert(receivedMessage)
                    .map(m -> new Reception<>(receptionTime,m))
                    .ifPresent(incomingMessages::offerLast);
        }

        @Override
        public void onPostMessage(@NonNull String postMessage) {}

        @Override
        public void onError(@NonNull Throwable throwable) {}
    }

    @Value
    private static class Reception<M> {

        @NonNull
        private final Instant time;

        @NonNull
        private final M message;
    }
}
