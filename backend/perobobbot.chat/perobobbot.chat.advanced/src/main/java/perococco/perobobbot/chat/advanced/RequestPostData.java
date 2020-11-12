package perococco.perobobbot.chat.advanced;

import lombok.NonNull;
import perobobbot.chat.advanced.ReceiptSlip;
import perobobbot.chat.advanced.Request;
import perobobbot.chat.advanced.RequestAnswerMatcher;
import perobobbot.lang.fp.TryResult;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

/**
 * @author perococco
 **/
public class RequestPostData<A,M> extends AbstractPostData<ReceiptSlip<A>, Request<A>,M> {

    @NonNull
    private final RequestAnswerMatcher<M> matcher;

    private Instant dispatchingTime = null;

    public RequestPostData(@NonNull Request<A> request, @NonNull RequestAnswerMatcher<M> matcher) {
        super(request);
        this.matcher = matcher;
    }

    @Override
    public @NonNull Optional<RequestPostData<?,M>> asRequestPostData() {
        return Optional.of(this);
    }

    @Override
    public void onMessagePosted(@NonNull Instant dispatchingTime) {
        this.dispatchingTime = dispatchingTime;
    }

    public void onRequestTimeout(@NonNull Duration duration) {
        completeExceptionallyWith(new TimeoutException("Timeout after "+duration));
    }

    public boolean tryToCompleteWith(@NonNull M incomingMessage, @NonNull Instant receptionTime) {
        final Optional<TryResult<Throwable,A>> match = matcher.performMatch(message(), incomingMessage);
        match.map(t -> t.map(a -> buildReceiptSlip(a,receptionTime)))
             .ifPresent(t -> t.accept(this::completeExceptionallyWith, this::completeWith));
        return match.isPresent();
    }

    @NonNull
    private BasicReceiptSlip<A> buildReceiptSlip(@NonNull A answer, @NonNull Instant receptionTime) {
        return new BasicReceiptSlip<>(dispatchingTime,receptionTime,message(),answer);
    }

}
