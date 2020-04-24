package perococco.bot.chat.advanced;

import bot.chat.advanced.event.ReceivedMessages;
import bot.common.lang.Looper;
import bot.common.lang.fp.Consumer1;
import bot.common.lang.fp.Consumer2;
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
    private final BlockingDeque<ReceivedMessages<M>> incomingMessages = new LinkedBlockingDeque<>();

    @NonNull
    private final List<RequestPostData<?,M>> pending = new LinkedList<>();

    @Override
    protected @NonNull IterationCommand performOneIteration() throws Exception {
        final ReceivedMessages<M> reception = incomingMessages.takeFirst();
        final Consumer1<M> performRendezVous = Consumer2.of(this::performRendezvousWithRequests)
                                                        .f2(reception.receptionTime());
        reception.messages()
                 .stream()
                 .filter(shouldPerformMatching)
                 .forEach(performRendezVous);
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

    public void onMessageReception(@NonNull ReceivedMessages<M> message) {
        incomingMessages.add(message);
    }

}
