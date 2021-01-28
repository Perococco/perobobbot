package perobobbot.poll;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.ImmutableBag;

@RequiredArgsConstructor
@Getter
public class PollResult {

    private final @NonNull ImmutableList<String> availableOptions;

    /**
     * Who votes for what
     */
    private final @NonNull ImmutableMap<Voter, ImmutableBag<String>> votes;

    /**
     * Number of votes per options
     */
    private final @NonNull ImmutableBag<String> voteCounts;

    public @NonNull ImmutableBag<String> getVotes(@NonNull Voter voter) {
        return votes.getOrDefault(voter,ImmutableBag.empty());
    }

    public int numberOfVotesFor(@NonNull String choice) {
        return voteCounts.count(choice);
    }

}
