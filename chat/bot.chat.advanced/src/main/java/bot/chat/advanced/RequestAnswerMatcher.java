package bot.chat.advanced;

import lombok.NonNull;

import java.util.Optional;

/**
 * @author perococco
 **/
public interface RequestAnswerMatcher<M> {

    boolean shouldPerformMatching(@NonNull M message);

    @NonNull
    <A> Optional<A> performMatch(@NonNull Request<A> request, @NonNull M message);
}
