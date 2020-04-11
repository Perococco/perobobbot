package perococco.bot.chat.advanced;

import bot.chat.advanced.Message;
import bot.chat.advanced.Request;
import bot.chat.advanced.RequestAnswerMatcher;
import lombok.NonNull;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

/**
 * @author perococco
 **/
public class RequestPostData<A> extends AbstractPostData<A> {

    @NonNull
    private final Request<A> request;

    @NonNull
    private final RequestAnswerMatcher matcher;

    public RequestPostData(@NonNull Request<A> request, @NonNull RequestAnswerMatcher matcher) {
        super(request);
        this.request = request;
        this.matcher = matcher;
    }

    @Override
    public @NonNull Optional<RequestPostData<?>> asRequestPostData() {
        return Optional.of(this);
    }

    @Override
    public void onMessagePosted() {}


    public void onRequestTimeout(@NonNull Duration duration) {
        completeExceptionallyWith(new TimeoutException("Timeout after "+duration));
    }

    public boolean tryToComplete(@NonNull Message incomingMessage) {
        final Optional<A> a = matcher.performMatch(request,incomingMessage);
        a.ifPresent(this::completeWith);
        return a.isPresent();
    }

}
