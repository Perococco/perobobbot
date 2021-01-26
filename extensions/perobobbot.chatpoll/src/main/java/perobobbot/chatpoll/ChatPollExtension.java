package perobobbot.chatpoll;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.extension.OverlayExtension;
import perobobbot.lang.MessageContext;
import perobobbot.lang.MessageDispatcher;
import perobobbot.lang.MessageHandler;
import perobobbot.lang.SubscriptionHolder;
import perobobbot.overlay.api.Overlay;
import perobobbot.poll.*;
import perococco.perobobbot.poll.PollConfiguration;

import java.time.Duration;

@Log4j2
public class ChatPollExtension extends OverlayExtension implements MessageHandler, PollListener {

    public static final String NAME = "ChatPoll";

    private final @NonNull MessageDispatcher messageDispatcher;

    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

    private TimedPoll timedPoll;

    public ChatPollExtension(@NonNull MessageDispatcher messageDispatcher, @NonNull Overlay overlay) {
        super(NAME, overlay);
        this.messageDispatcher = messageDispatcher;
    }

    public void start(@NonNull ImmutableSet<String> options, @NonNull PollConfiguration configuration, @NonNull Duration duration) {
        subscriptionHolder.replaceWith(() -> messageDispatcher.addListener(this));
        timedPoll = PollFactory.getFactory().createPoll(options, configuration).createTimedFromThis();
        timedPoll.addPollListener(this);
        timedPoll.start(duration).whenComplete((result,error) -> {
            if (error != null) {
                LOG.error("Poll complete exceptionally",error);
            }
            timedPoll.stop();
        });
    }

    public void stop() {
        timedPoll.stop();
        subscriptionHolder.unsubscribe();
    }

    @Override
    public void handleMessage(@NonNull MessageContext messageContext) {
        timedPoll.addVote(Voter.from(messageContext.getMessageOwner()), messageContext.getContent());
    }

    @Override
    public void onPollResult(@NonNull PollResult result, boolean isFinal, @NonNull Duration remainingTime) {
    }

    @Override
    public void onPollFailed(@NonNull PollResult lastResult) {
        System.err.println("Poll failed");
    }

}
