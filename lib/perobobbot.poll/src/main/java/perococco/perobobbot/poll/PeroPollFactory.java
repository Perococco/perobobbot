package perococco.perobobbot.poll;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.poll.Poll;
import perobobbot.poll.PollFactory;

public class PeroPollFactory implements PollFactory {

    @Override
    public @NonNull Poll createPoll(@NonNull ImmutableSet<String> pollOptions, @NonNull PollConfiguration configuration) {
        return SimplePoll.createClosed(pollOptions, configuration);
    }

    @Override
    public @NonNull Poll createOrderedPoll(@NonNull ImmutableList<String> pollOptions, @NonNull PollConfiguration configuration) {
        return SimplePoll.createCloseOrdered(pollOptions,configuration);
    }

    @Override
    public @NonNull Poll createOpenPoll(@NonNull PollConfiguration configuration) {
        return SimplePoll.createOpen(configuration);
    }

}
