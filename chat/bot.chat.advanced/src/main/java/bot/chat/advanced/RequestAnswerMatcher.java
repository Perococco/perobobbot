package bot.chat.advanced;

import bot.common.lang.fp.TryResult;
import lombok.NonNull;

import java.util.Optional;

/**
 * @author perococco
 **/
public interface RequestAnswerMatcher<M> {

    boolean shouldPerformMatching(@NonNull M message);

    @NonNull
    <A> Optional<TryResult<Throwable,A>> performMatch(@NonNull Request<A> request, @NonNull M message);
}
