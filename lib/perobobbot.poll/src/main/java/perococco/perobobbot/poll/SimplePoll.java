package perococco.perobobbot.poll;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Bag;
import perobobbot.lang.HashBag;
import perobobbot.lang.StringTools;
import perobobbot.poll.Poll;
import perobobbot.poll.PollResult;
import perobobbot.poll.TimedPoll;
import perobobbot.poll.Voter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SimplePoll implements Poll {

    public static @NonNull Poll createClosed(@NonNull ImmutableSet<String> availableOptions, @NonNull PollConfiguration configuration) {
        return new SimplePoll(availableOptions, availableOptions::contains, configuration);
    }

    public static Poll createCloseOrdered(@NonNull  ImmutableList<String> pollOptions, @NonNull PollConfiguration configuration) {
        return createClosed(ImmutableSet.copyOf(pollOptions),configuration);
    }

    public static @NonNull Poll createOpen(@NonNull PollConfiguration configuration) {
        return new SimplePoll(null, StringTools::hasData, configuration);
    }

    private final ImmutableSet<String> availableOptions;

    private final @NonNull Predicate<String> idValidChoice;

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
        final ImmutableList<String> availableOptions;
        if (this.availableOptions != null) {
            availableOptions = ImmutableList.copyOf(this.availableOptions);
        } else {
            availableOptions = ImmutableList.copyOf(voteCounts.keySet());
        }

        final var votes = votePerVoter.entrySet()
                                      .stream()
                                      .collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, e -> e.getValue().toImmutable()));
        return new PollResult(availableOptions, votes, voteCounts.toImmutable());
    }

    @Override
    public boolean addVote(@NonNull Voter voter, @NonNull String idVoted) {
        if (userCannotVote(voter)) {
            return false;
        }
        if (availableOptions.contains(idVoted)) {
            voteCounts.add(idVoted, 1);
            votePerVoter.computeIfAbsent(voter, k -> new HashBag<>()).add(idVoted, 1);
            return true;
        }
        return false;
    }

    private boolean userCannotVote(@NonNull Voter voter) {
        return !configuration.isMultipleVoteAllowed() && voterHasVotedAlready(voter);
    }

    private boolean voterHasVotedAlready(Voter voter) {
        return votePerVoter.containsKey(voter);
    }

    @Override
    public @NonNull TimedPoll createTimedFromThis() {
        return new PeroTimedPoll(this);
    }
}
