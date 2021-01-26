package perobobbot.poll;

import lombok.NonNull;

public interface Poll {

    void clear();

    /**
     * Add a vote
     * @param user the user that voted
     * @param idVoted the id of the option used by the user
     */
    void addVote(@NonNull Voter user, @NonNull String idVoted);

    default void addVote(@NonNull Vote vote) {
        addVote(vote.getVoter(),vote.getChoice());
    }

    @NonNull PollResult getCurrentCount();

    @NonNull TimedPoll createTimedFromThis();


}
