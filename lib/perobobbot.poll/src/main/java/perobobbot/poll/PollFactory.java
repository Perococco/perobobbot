package perobobbot.poll;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perococco.perobobbot.poll.PeroPollFactory;
import perococco.perobobbot.poll.PollConfiguration;

public interface PollFactory {

    @NonNull Poll createPoll(@NonNull ImmutableSet<String> pollOptions, @NonNull PollConfiguration configuration);

    @NonNull Poll createOpenPoll(@NonNull PollConfiguration configuration);


    static @NonNull PollFactory getFactory() {
        return new PeroPollFactory();
    }


}
