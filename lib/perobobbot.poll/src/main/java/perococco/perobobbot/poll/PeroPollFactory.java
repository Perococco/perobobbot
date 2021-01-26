package perococco.perobobbot.poll;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.poll.Poll;
import perobobbot.poll.PollFactory;
import perobobbot.poll.TimedPoll;

public class PeroPollFactory implements PollFactory {

    @Override
    public @NonNull Poll createPoll(@NonNull ImmutableSet<String> pollOptions, @NonNull PollConfiguration configuration) {
        return SimplePoll.create(pollOptions, configuration);
    }

    @Override
    public @NonNull TimedPoll createTimedPoll(@NonNull ImmutableSet<String> pollOptions, @NonNull PollConfiguration configuration) {
        return new PeroTimedPoll(createPoll(pollOptions,configuration));
    }
}
