package perobobbot.poll;

import lombok.NonNull;
import perobobbot.lang.Subscription;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

public interface TimedPoll {

    @NonNull CompletionStage<PollResult> start(@NonNull Duration duration);

    void stop();

    /**
     * Add a vote
     * @param voter the user that voted
     * @param choice the id of the option used by the user
     */
    void addVote(@NonNull Voter voter, @NonNull String choice);

    @NonNull Subscription addPollListener(@NonNull PollListener pollListener);

}
