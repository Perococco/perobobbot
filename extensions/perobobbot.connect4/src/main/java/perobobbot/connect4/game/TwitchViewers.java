package perobobbot.connect4.game;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.connect4.TokenType;
import perobobbot.lang.MessageContext;
import perobobbot.lang.MessageDispatcher;
import perobobbot.lang.MessageHandler;
import perobobbot.lang.Subscription;
import perobobbot.poll.*;
import perococco.perobobbot.poll.PollConfiguration;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
public class TwitchViewers extends AbstractPlayer implements Player {

    public static final Duration DEFAULT_POLL_DURATION = Duration.ofSeconds(30);

    public static @NonNull Player.Factory factory(@NonNull MessageDispatcher messageDispatcher, @NonNull int duration) {
        final Duration pollDuration;
        if (duration <= 0) {
            pollDuration = DEFAULT_POLL_DURATION;
        } else {
            pollDuration = Duration.ofSeconds(duration);
        }

        return (team, controller) -> new TwitchViewers(team, controller, messageDispatcher, pollDuration);
    }

    @Getter
    private final @NonNull TokenType team;

    private final @NonNull Connect4OverlayController controller;

    private final @NonNull MessageDispatcher messageDispatcher;

    private final @NonNull Duration pollDuration;


    @Override
    protected int getNextMove(@NonNull Connect4State state) throws Throwable {
        return new NextMoveGetter(state).getNextMove();
    }

    @RequiredArgsConstructor
    private class NextMoveGetter implements @NonNull PollListener, @NonNull MessageHandler {

        private final @NonNull Connect4State state;

        private ImmutableList<String> optionList;

        private TimedPoll poll;

        public int getNextMove() throws Throwable {
            this.createOptionList();
            this.createPoll();
            return this.launchPollAndWaitForChoice();
        }

        private int launchPollAndWaitForChoice() throws Throwable {
            return Subscription.subscribeAndTry(
                    () -> Subscription.multi(
                            messageDispatcher.addListener(this),
                            poll.addPollListener(this),
                            controller.setPollStarted(team, pollDuration)),
                    () -> poll.start(pollDuration)
                          .thenApply(this::findOptionWithMostVotes)
                          .thenApply(o -> o.orElseGet(state::pickOneColumn))
                          .toCompletableFuture()
                          .get());
        }

        private Optional<Integer> findOptionWithMostVotes(@NonNull PollResult pollResult) {
            int indexOfMax = -1;
            int valueOfMax = -1;
            for (int i = 0; i < optionList.size(); i++) {
                var value = pollResult.numberOfVotesFor(optionList.get(i));
                if (value != 0 && valueOfMax < value) {
                    indexOfMax = i;
                    valueOfMax = value;
                }
            }
            if (indexOfMax>=0) {
                return Optional.of(state.getIndicesOfFreeColumns()[indexOfMax]);
            }

            return Optional.empty();
        }

        private void createOptionList() {
            this.optionList = state.streamIndicesOfFreeColumns()
                                   .map(i -> i + 1)
                                   .mapToObj(Integer::toString)
                                   .collect(ImmutableList.toImmutableList());
        }

        private void createPoll() {
            this.poll = PollFactory.getFactory()
                                   .createOrderedPoll(optionList, new PollConfiguration(false))
                                   .createTimedFromThis();
        }


        @Override
        public void onPollResult(@NonNull PollResult result, boolean isFinal, @NonNull Duration remainingTime) {
            updateHistogram(result);
        }

        @Override
        public void onPollFailed(@NonNull PollResult lastResult) {
            updateHistogram(lastResult);
        }

        private void updateHistogram(@NonNull PollResult result) {
            for (int i = 0; i < optionList.size(); i++) {
                controller.setHistogramValues(i,result.numberOfVotesFor(optionList.get(i)));
            }
        }

        @Override
        public void handleMessage(@NonNull MessageContext messageContext) {
            poll.addVote(Voter.from(messageContext.getMessageOwner()), messageContext.getContent());
        }
    }
}
