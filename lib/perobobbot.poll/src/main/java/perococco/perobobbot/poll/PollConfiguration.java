package perococco.perobobbot.poll;

import lombok.Value;

@Value
public class PollConfiguration {

    /**
     * Can a user vote multiple of times
     */
    boolean multipleVoteAllowed;

    /**
     * Are the votes case sensitive
     */
    boolean caseSensitive;
}
