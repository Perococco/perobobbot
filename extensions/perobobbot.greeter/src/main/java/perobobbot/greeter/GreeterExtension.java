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
import perobobbot.lang.fp.Value2;
import perobobbot.messaging.ChatController;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@RequiredArgsConstructor
public class GreeterExtension implements Extension {

    @NonNull
    @Getter
    private final String name = GreeterExtensionFactory.NAME;

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

    public GreeterExtension(@NonNull IO io, @NonNull ChatController chatController) {
        this(io, chatController, new DefaultGreetingMessageCreator());
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
            final ImmutableMap<ChannelInfo, ImmutableSet<Value2<Bot, User>>> greeters;

            greeters = identity.mutateAndGetFromOldState(ClearGreetingIssuers.create(), HelloState::getGreetersPerChannel)
                               .toCompletableFuture()
                               .get();

            greeters.entrySet()
                    .stream()
                    .flatMap(e -> createGreeter(e.getKey(), e.getValue()))
                    .forEach(Greeter::execute);

        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            LOG.warn("Error while greeting", t);
        }
    }

    private @NonNull Stream<Greeter> createGreeter(@NonNull ChannelInfo channelInfo, @NonNull ImmutableSet<Value2<Bot, User>> botAndUser) {
        final Map<Bot, ImmutableSet<User>> userPerBot = botAndUser.stream()
                                                                  .collect(
                                                                          Collectors.groupingBy(Value2::getA,
                                                                                                Collectors.mapping(Value2::getB,
                                                                                                                   ImmutableSet.toImmutableSet())
                                                                          ));

        return userPerBot.entrySet()
                         .stream()
                         .map(e -> new Greeter(io, messageCreator, channelInfo, e.getKey(), e.getValue()));
    }


}
