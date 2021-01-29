package perobobbot.chatpoll;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.extension.OverlayExtension;
import perobobbot.lang.*;
import perobobbot.overlay.api.Overlay;
import perobobbot.poll.*;
import perobobbot.rendering.histogram.HistogramStyle;
import perococco.perobobbot.poll.PollConfiguration;

import java.awt.*;
import java.time.Duration;

@Log4j2
public class ChatPollExtension extends OverlayExtension implements MessageHandler {

    public static final String NAME = "ChatPoll";

    private final @NonNull MessageDispatcher messageDispatcher;

    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

    private TimedPoll timedPoll;


    public ChatPollExtension(@NonNull MessageDispatcher messageDispatcher, @NonNull Overlay overlay) {
        super(NAME, overlay);
        this.messageDispatcher = messageDispatcher;
    }

    public void start(@NonNull ImmutableSet<String> options, @NonNull PollConfiguration configuration, @NonNull Duration duration) {
        timedPoll = PollFactory.getFactory()
                               .createPoll(options, configuration)
                               .createTimedFromThis();

        var overlay = new ChatPollOverlay(options.size(), HistogramStyle.builder()
                                                                        .margin(10)
                                                                        .spacing(2)
                                                                        .build());
        attachClient(overlay);
        subscriptionHolder.replaceWith(
                () -> Subscription.multi(
                messageDispatcher.addListener(this),
                timedPoll.addPollListener(overlay))
        );

        timedPoll.start(duration).whenComplete((result,error) -> {
            if (error != null) {
                LOG.error("Poll complete exceptionally",error);
            }
            this.stop();
        });
    }

    public void stop() {
        detachClient();
        timedPoll.stop();
        subscriptionHolder.unsubscribe();
    }

    @Override
    public void handleMessage(@NonNull MessageContext messageContext) {
        timedPoll.addVote(Voter.from(messageContext.getMessageOwner()), messageContext.getContent());
    }

}
