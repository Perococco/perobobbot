package perobobbot.chat.advanced;

import lombok.NonNull;
import perobobbot.common.lang.fp.TryResult;

import java.util.Optional;

/**
 * An object that provides some methods to perform the
 * rendez-vous between a request and its answer.
 *
 * @author perococco
 * @param <M></M> the type of message sent by the chat
 **/
public interface RequestAnswerMatcher<M> {

    /**
     * Some message from the chat are not answers from a request (like private message). In that
     * case there is no need to try to match it with requests waiting for an answer.
     *
     * This method is used to determined if a message should be used or not for the matching process.
     *
     * @param message a message received from the chat
     * @return true if the provided message can be used to perform a match with the requests
     */
    boolean shouldPerformMatching(@NonNull M message);

    /**
     * <p>Perform a match between a request and a message received from the chat.
     * The result is an optional.</p>
     *
     * <p>If the message is not the answer of the request, the result must be
     * an empty optional.</p>
     *
     * <p> Otherwise, if the message is the answer of the request, the result must be a
     * optional containing a {@link TryResult}. A <code>TryResult</code> is used since the received message
     * might indicates that the request failed (like authentication error) and this should be transform
     * in a failed <code>TryResult</code>.</p>
     *
     *
     * @param request the request waiting for an answer
     * @param message the received message
     * @param <A> the type of the answer of the request
     * @return an optional containing the result of the match if the message is the answer of the request,
     * an empty optional if the message is not the answer of the request
     */
    @NonNull
    <A> Optional<TryResult<Throwable,A>> performMatch(@NonNull Request<A> request, @NonNull M message);
}
