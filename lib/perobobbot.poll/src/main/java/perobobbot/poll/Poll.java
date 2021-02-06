package perobobbot.poll;

import lombok.NonNull;

public interface Poll {

    void clear();

    /**
     * Add a vote
     * @param user the user that voted
     * @param idVoted the id of the option used by the user
     * @return true if the vote is valid, false otherwise
     */
    boolean addVote(@NonNull Voter user, @NonNull String idVoted);

    default boolean addVote(@NonNull Vote vote) {
        return addVote(vote.getVoter(),vote.getChoice());
    }

    @NonNull PollResult getCurrentCount();

    @NonNull TimedPoll createTimedFromThis();


}
