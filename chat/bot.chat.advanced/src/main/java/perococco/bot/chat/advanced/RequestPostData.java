package perococco.bot.chat.advanced;

import bot.chat.advanced.ReceiptSlip;
import bot.chat.advanced.Request;
import bot.chat.advanced.RequestAnswerMatcher;
import lombok.NonNull;

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
        final Optional<A> a = matcher.performMatch(message(),incomingMessage);
        a.map(v -> new BasicReceiptSlip<>(dispatchingTime,receptionTime,message(),v)).ifPresent(this::completeWith);
        return a.isPresent();
    }

}
