package perococco.perobobbot.poll;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Bag;
import perobobbot.lang.HashBag;
import perobobbot.lang.fp.Function1;
import perobobbot.poll.Poll;
import perobobbot.poll.PollResult;
import perobobbot.poll.TimedPoll;
import perobobbot.poll.Voter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SimplePoll implements Poll {

    public static @NonNull Poll createClosed(@NonNull ImmutableSet<String> availableOptions, @NonNull PollConfiguration configuration) {

        return new SimplePoll(availableOptions,createOptionModifier(availableOptions,configuration), configuration);
    }

    public static Poll createCloseOrdered(@NonNull  ImmutableList<String> pollOptions, @NonNull PollConfiguration configuration) {
        return createClosed(ImmutableSet.copyOf(pollOptions),configuration);
    }

    public static @NonNull Poll createOpen(@NonNull PollConfiguration configuration) {
        final Function1<String,Optional<String>> modifier;
        if (configuration.isCaseSensitive()) {
            modifier = Optional::of;
        }
        else {
            modifier = s -> Optional.of(s.toLowerCase());
        }
        return new SimplePoll(null, modifier, configuration);
    }

    private static Function1<String,Optional<String>> createOptionModifier(@NonNull ImmutableCollection<String> options, @NonNull PollConfiguration configuration) {
        if (configuration.isCaseSensitive()) {
            return s-> Optional.of(s).filter(options::contains);
        }
        return s -> options.stream().filter(s::equalsIgnoreCase).findFirst();
    }

    private final ImmutableSet<String> availableOptions;

    private final @NonNull Function1<String, Optional<String>> idVoteProcessor;

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

        final var vote = idVoteProcessor.f(idVoted);

        vote.ifPresent(v -> {
            voteCounts.add(v, 1);
            votePerVoter.computeIfAbsent(voter, k -> new HashBag<>()).add(v, 1);
        });

        return vote.isPresent();
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
