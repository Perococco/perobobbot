package perococco.perobobbot.poll;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Bag;
import perobobbot.lang.HashBag;
import perobobbot.poll.Poll;
import perobobbot.poll.PollResult;
import perobobbot.poll.Voter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class SimplePoll implements Poll {

    public static @NonNull Poll create(@NonNull ImmutableSet<String> availableOptions, @NonNull PollConfiguration configuration) {
        return new SimplePoll(availableOptions, configuration);
    }


    private final @NonNull ImmutableSet<String> availableOptions;

    private final @NonNull PollConfiguration configuration;

    private final @NonNull Map<Voter, Bag<String>> votePerVoter = new HashMap<>();

    private final @NonNull Bag<String> voteCounts = new HashBag<>();

    @Override
    public void clear() {
        this.votePerVoter.clear();
        this.voteCounts.clear();
    }

    @Override
    public @NonNull PollResult getCurrentCount() {
        final var votes = votePerVoter.entrySet()
                                      .stream()
                                      .collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, e -> e.getValue().toImmutable()));
        return new PollResult(availableOptions,votes,voteCounts.toImmutable());
    }

    @Override
    public void addVote(@NonNull Voter voter, @NonNull String idVoted) {
        if (userCannotVote(voter)) {
            return;
        }
        if (availableOptions.contains(idVoted)) {
            voteCounts.add(idVoted,1);
            votePerVoter.computeIfAbsent(voter, k -> new HashBag<>()).add(idVoted, 1);
        }
    }

    private boolean userCannotVote(@NonNull Voter voter) {
        return !configuration.isMultipleVoteAllowed() && voterHasVotedAlready(voter);
    }

    private boolean voterHasVotedAlready(Voter voter) {
        return votePerVoter.containsKey(voter);
    }
}
