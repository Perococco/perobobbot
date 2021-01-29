package perobobbot.connect4.game;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.connect4.Connect4OverlayController;
import perobobbot.connect4.TokenType;
import perobobbot.lang.MessageContext;
import perobobbot.lang.MessageDispatcher;
import perobobbot.lang.MessageHandler;
import perobobbot.lang.Subscription;
import perobobbot.poll.*;
import perococco.perobobbot.poll.PollConfiguration;

import java.time.Duration;

@RequiredArgsConstructor
public class TwitchViewers extends AbstractPlayer implements Player {

    @Getter
    private final @NonNull TokenType team;

    private final @NonNull Connect4OverlayController controller;

    private final @NonNull MessageDispatcher messageDispatcher;

    private final @NonNull Duration pollDuration;


    @Override
    protected int getNextMove(@NonNull Connect4State state) throws Throwable {
        return new NextMoveGetter(getTeam(), state).getNextMove();
    }

    @RequiredArgsConstructor
    private class NextMoveGetter implements @NonNull PollListener, @NonNull MessageHandler {

        private final @NonNull TokenType team;

        private final @NonNull Connect4State state;

        private ImmutableList<String> optionList;

        private TimedPoll poll;

        public int getNextMove() throws Throwable {
            this.createOptionList();
            this.createPoll();
            return this.launchPollAndWait();
        }

        private int launchPollAndWait() throws Throwable {
            final Subscription subscription = Subscription.multi(
                    messageDispatcher.addListener(this),
                    poll.addPollListener(this)
            );

            var result = -1;
            try {
                controller.setPollStarted(getTeam(), pollDuration);
                int indexOfMax = -1;
                final PollResult pollResult = this.poll.start(pollDuration).toCompletableFuture().get();
                int valueOfMax = -1;
                for (int i = 0; i < optionList.size(); i++) {
                    var value = pollResult.numberOfVotesFor(optionList.get(i));
                    if (valueOfMax < value) {
                        indexOfMax = i;
                        valueOfMax = value;
                    }
                }
                result = indexOfMax < 0 ? state.pickOneColumn() : indexOfMax;
                return result;
            } finally {
                controller.setPollDone();
                subscription.unsubscribe();
            }
        }


        private void createOptionList() {
            this.optionList = state.getIndexOfFreeColumns()
                                   .map(i -> i + 1)
                                   .mapToObj(Integer::toString)
                                   .collect(ImmutableList.toImmutableList());
        }

        private void createPoll() {
            this.poll = PollFactory.getFactory()
                                   .createOrderedPoll(optionList, new PollConfiguration(true))
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
            final var votes = optionList.stream().mapToInt(result::numberOfVotesFor).toArray();
            controller.setHistogramValues(votes);
        }

        @Override
        public void handleMessage(@NonNull MessageContext messageContext) {
            poll.addVote(Voter.from(messageContext.getMessageOwner()), messageContext.getContent());
        }
    }
}
