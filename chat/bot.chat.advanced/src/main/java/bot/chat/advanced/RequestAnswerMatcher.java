package bot.chat.advanced;

import lombok.NonNull;

import java.util.Optional;

/**
 * @author perococco
 **/
public interface RequestAnswerMatcher {

    @NonNull
    <A> Optional<A> performMatch(@NonNull Request<A> request, @NonNull Message message);
}
