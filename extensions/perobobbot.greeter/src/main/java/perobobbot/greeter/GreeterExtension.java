package perobobbot.greeter;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import perobobbot.chat.core.IO;
import perobobbot.extension.Extension;
import perobobbot.greeter.mutation.ClearGreetingIssuers;
import perobobbot.greeter.spring.GreeterExtensionFactory;
import perobobbot.lang.*;
import perobobbot.messaging.ChatController;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Log4j2
@RequiredArgsConstructor
public class GreeterExtension implements Extension {

    @NonNull
    @Getter
    private final String name = GreeterExtensionFactory.NAME;

    private final Bot bot;

    @NonNull
    private final IO io;

    @NonNull
    private final ChatController chatController;

    @NonNull
    private final GreetingMessageCreator messageCreator;

    @NonNull
    private final HelloIdentity identity = new HelloIdentity();

    private Future<?> future = null;

    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

    public GreeterExtension(@NonNull Bot bot, @NonNull IO io, @NonNull ChatController chatController) {
        this(bot, io, chatController, new DefaultGreetingMessageCreator());
    }

    @Override
    @Synchronized
    public void enable() {
        if (future != null) {
            return;
        }
        future = TASK_EXECUTOR.scheduleWithFixedDelay(
                this::performGreetings,
                10,
                5,
                TimeUnit.SECONDS
        );
        subscriptionHolder.replaceWith(() -> chatController.addListener(new HelloMessageHandler(identity)));
    }

    @Override
    @Synchronized
    public void disable() {
        subscriptionHolder.unsubscribe();
        if (future != null) {
            future.cancel(true);
            future = null;
        }
    }

    @Override
    @Synchronized
    public boolean isEnabled() {
        return future != null;
    }

    private void performGreetings() {
        try {
            final ImmutableMap<ChannelInfo, ImmutableSet<User>> greeters;

            greeters = identity.mutateAndGetFromOldState(ClearGreetingIssuers.create(), HelloState::getGreetersPerChannel)
                               .toCompletableFuture()
                               .get();

            greeters.entrySet()
                    .stream()
                    .map(e -> new Greeter(io, messageCreator, bot, e.getKey(), e.getValue()))
                    .forEach(Greeter::execute);

        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            LOG.warn("Error while greeting", t);
        }
    }


}