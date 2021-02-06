package perococco.perobobbot.poll;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.lang.Listeners;
import perobobbot.lang.Subscription;
import perobobbot.lang.ThreadFactories;
import perobobbot.lang.ThrowableTool;
import perobobbot.poll.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@RequiredArgsConstructor
public class PeroTimedPoll implements TimedPoll {

    public static final long ONE_SECOND_IN_NANO = Duration.ofSeconds(1).toNanos();

    private final @NonNull Poll poll;

    private final Listeners<PollListener> listeners = new Listeners<>();

    //WARNING : might be overrun in case of a big chat room or two small cooldown between vote
    private final BlockingDeque<Vote> pendingVotes = new LinkedBlockingDeque<>();

    private Thread runnerThread = null;

    @Override
    @Synchronized
    public @NonNull CompletionStage<PollResult> start(@NonNull Duration duration, boolean startTimerOnFirstVote) {
        if (runnerThread != null) {
            runnerThread.interrupt();
            runnerThread = null;
        }
        final var onPollDoneFuture = new CompletableFuture<PollResult>();

        runnerThread = ThreadFactories.daemon("Timed Poll Thread %d").newThread(new Runner(duration, startTimerOnFirstVote, onPollDoneFuture));
        runnerThread.start();
        return onPollDoneFuture;
    }

    @Override
    @Synchronized
    public void stop() {
        if (runnerThread != null) {
            runnerThread.interrupt();
            runnerThread = null;
        }
    }

    @Override
    public void addVote(@NonNull Voter voter, @NonNull String choice) {
        pendingVotes.add(voter.createVote(choice));
    }

    @Override
    public @NonNull Subscription addPollListener(@NonNull PollListener pollListener) {
        return listeners.addListener(pollListener);
    }

    @RequiredArgsConstructor
    private class Runner implements Runnable {

        private final @NonNull Duration duration;

        private final boolean startTimerOnFirstVote;

        private final @NonNull CompletableFuture<PollResult> onPollDoneFuture;

        private final List<Vote> buffer = new ArrayList<>();


        @Override
        public void run() {
            try {
                listeners.warnListeners(PollListener::onPollStarted);
                poll.clear();
                doRun();
            } catch (Throwable t) {
                ThrowableTool.interruptThreadIfCausedByInterruption(t);
                final var pollResult = poll.getCurrentCount();
                listeners.warnListeners(l -> l.onPollFailed(pollResult));
                onPollDoneFuture.completeExceptionally(t);
            }
        }

        public void doRun() throws InterruptedException {
            if (startTimerOnFirstVote) {
                boolean validVotes;
                do  {
                    validVotes = listenForVotesAndUpdatePoll(-1);
                } while (!validVotes && !Thread.currentThread().isInterrupted());
                warnOnNewPollResult(duration);
            }
            listeners.warnListeners(PollListener::onPollTimerStarted);

            final long endingTime = System.nanoTime() + duration.toNanos();
            while (!Thread.interrupted()) {
                var remainingTime = endingTime - System.nanoTime();
                if (remainingTime > 0) {
                    listenForVotesAndUpdatePoll(Math.min(remainingTime, ONE_SECOND_IN_NANO));
                    remainingTime = endingTime - System.nanoTime();
                }
                warnOnNewPollResult(Duration.ofNanos(Math.max(0, remainingTime)));
                if (remainingTime <= 0) {
                    break;
                }
            }

            onPollDoneFuture.complete(poll.getCurrentCount());
        }

        private void warnOnNewPollResult(@NonNull Duration remainingTime) {
            final var pollResult = poll.getCurrentCount();
            listeners.warnListeners(l -> l.onPollResult(pollResult, remainingTime.isZero(), remainingTime));
        }

        private boolean listenForVotesAndUpdatePoll(long durationToWaitForVote) throws InterruptedException {
            final var nbValidVotes = retrievePendingVotes(durationToWaitForVote).stream()
                                                                                .map(poll::addVote)
                                                                                .filter(t -> t)
                                                                                .count();
            return nbValidVotes>=1;
        }

        private List<Vote> retrievePendingVotes(long durationToWaitForVote) throws InterruptedException {
            buffer.clear();
            final Vote vote;
            if (durationToWaitForVote <= 0) {
                vote = pendingVotes.take();
            } else {
                vote = pendingVotes.poll(durationToWaitForVote, TimeUnit.NANOSECONDS);
            }
            if (vote != null) {
                buffer.add(vote);
                pendingVotes.drainTo(buffer);
            }
            return buffer;
        }
    }

}
