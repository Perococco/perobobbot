package perococco.bot.chat.advanced;

import bot.chat.advanced.AdvancedChatListener;
import bot.chat.advanced.Message;
import bot.chat.advanced.MessageConverter;
import bot.chat.core.Chat;
import bot.chat.core.ChatListener;
import bot.common.lang.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author perococco
 **/
@Log4j2
@RequiredArgsConstructor
public class Receiver extends Looper {

    @NonNull
    private final Chat chat;

    @NonNull
    private final Listeners<AdvancedChatListener> listeners;

    @NonNull
    private final BlockingDeque<RequestPostData<?>> requestPostData;

    @NonNull
    private final MessageConverter messageFactory;

    @NonNull
    private final BlockingDeque<Message> incomingMessages = new LinkedBlockingDeque<>();

    @NonNull
    private final List<RequestPostData<?>> pending = new LinkedList<>();

    private Subscription subscription = Subscription.NONE;


    @Override
    protected void beforeLooping() {
        subscription = chat.addChatListener(new Listener());
    }

    @Override
    protected @NonNull IterationCommand performOneIteration() throws Exception {
        final Message message = incomingMessages.takeFirst();
        listeners.warnListeners(AdvancedChatListener::onReceivedMessage,message);

        final boolean messageHandled = this.performRendezvousWithRequests(message);

        if (!messageHandled) {
            LOG.debug("Message without request : {}",message);
        }

        return IterationCommand.CONTINUE;
    }

    private boolean performRendezvousWithRequests(@NonNull Message incomingMessage) {
        this.preparePendingRequests();
        for (RequestPostData<?> postData : pending) {
            if (postData.tryToComplete(incomingMessage)) {
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
            incomingMessages.offerLast(messageFactory.convert(receivedMessage));
        }

        @Override
        public void onPostMessage(@NonNull String postMessage) {}

        @Override
        public void onError(@NonNull Throwable throwable) {}
    }
}
