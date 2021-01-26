package perococco.perobobbot.poll;

import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.lang.Platform;
import perobobbot.poll.PollResult;
import perobobbot.poll.Voter;

import java.util.stream.Stream;

public class TestSingleVotePoll {


    private static Voter batman = new Voter("Batman", Platform.TWITCH);
    private static Voter superman = new Voter("Superman", Platform.TWITCH);
    private static Voter catwoman = new Voter("Catwoman", Platform.TWITCH);
    private static Voter wonderwomman = new Voter("Wonderwoman", Platform.TWITCH);

    private static ImmutableSet<String> options;

    private PollResult pollResult;

    public static Stream<Voter> allVoters() {
        return Stream.of(batman,superman,catwoman,wonderwomman);
    }

    private static Stream<Arguments> expectedVotes() {
        return Stream.of(
                Arguments.of("A",1),
                Arguments.of("B",2),
                Arguments.of("C",1),
                Arguments.of("D",0),
                Arguments.of("E",0)
        );
    }

    @BeforeEach
    public void setUp() {
        this.options = ImmutableSet.of("A","B","C","D","E");
        final var poll = SimplePoll.create(this.options, new PollConfiguration(false));

        poll.addVote(batman,"A");
        poll.addVote(superman,"B");
        poll.addVote(catwoman,"C");
        poll.addVote(wonderwomman,"B");

        poll.addVote(batman,"D");

        this.pollResult = poll.getCurrentCount();
    }

    @ParameterizedTest
    @MethodSource("allVoters")
    public void shouldHaveVotedOnce(Voter voter) {
        final var votes = this.pollResult.getVotes(voter);
        Assertions.assertEquals(1,votes.size());
    }

    @ParameterizedTest
    @MethodSource("expectedVotes")
    public void shouldHaveRightAmountOfVote(String choice, int expectedCount) {
        final var actualCount = this.pollResult.numberOfVotesFor(choice);
        Assertions.assertEquals(expectedCount,actualCount,"Number of vote for "+choice);
    }



}
