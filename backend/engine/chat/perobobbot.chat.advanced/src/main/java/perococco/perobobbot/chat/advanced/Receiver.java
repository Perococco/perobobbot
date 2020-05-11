package perococco.perobobbot.chat.advanced;

import perobobbot.chat.advanced.event.ReceivedMessage;
import perobobbot.common.lang.Looper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
    private final Predicate<? super M> shouldPerformMatching;

    @NonNull
    private final BlockingDeque<RequestPostData<?,M>> requestPostData;

    @NonNull
    private final BlockingDeque<ReceivedMessage<M>> incomingMessages = new LinkedBlockingDeque<>();

    @NonNull
    private final List<RequestPostData<?,M>> pending = new LinkedList<>();

    @Override
    protected @NonNull IterationCommand performOneIteration() throws Exception {
        final ReceivedMessage<M> reception = incomingMessages.takeFirst();

        final M message = reception.getMessage();
        if (shouldPerformMatching.test(message)) {
            performRendezvousWithRequests(message,reception.getReceptionTime());
        }

        return IterationCommand.CONTINUE;
    }

    private void performRendezvousWithRequests(@NonNull M message, @NonNull Instant receptionTime) {
        this.preparePendingRequests();
        for (RequestPostData<?,M> postData : pending) {
            if (postData.tryToCompleteWith(message, receptionTime)) {
                return;
            }
        }
        LOG.debug("Message without request : {}", message);
    }

    private void preparePendingRequests() {
        requestPostData.drainTo(pending);
        pending.removeIf(RequestPostData::isCompleted);
    }

    public void onMessageReception(@NonNull ReceivedMessage<M> message) {
        incomingMessages.add(message);
    }

}
