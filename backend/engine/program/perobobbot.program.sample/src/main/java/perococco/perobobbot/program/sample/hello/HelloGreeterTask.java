package perococco.perobobbot.program.sample.hello;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import perobobbot.common.lang.*;
import perobobbot.common.lang.fp.Function1;
import perobobbot.program.core.BackgroundTask;
import perobobbot.service.core.Services;
import perococco.perobobbot.program.sample.hello.mutation.ClearGreetingIssuers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
public class HelloGreeterTask implements BackgroundTask {

    @NonNull
    private final IO io;

    @NonNull
    private final HelloIdentity identity;

    private ScheduledFuture<?> future;

    public HelloGreeterTask(@NonNull Services services, @NonNull HelloIdentity helloIdentity) {
        this.io = services.getService(IO.class);
        this.identity = helloIdentity;
    }

    @Override
    @Synchronized
    public void start() {
        if (future != null) {
            return;
        }
        future = TASK_EXECUTOR.scheduleWithFixedDelay(
                this::performGreetings,
                10,
                5,
                TimeUnit.SECONDS
        );
    }

    @Override
    @Synchronized
    public void stop() {
        if (future != null) {
            future.cancel(true);
            future = null;
        }
    }

    private void performGreetings() {
        try {
            final ImmutableMap<ChannelInfo, ImmutableSet<User>> greeters;

            greeters = identity.mutateAndGetFromOldState(ClearGreetingIssuers.create(), HelloState::getGreetersPerChannel)
                               .toCompletableFuture()
                               .get();

            greeters.entrySet()
                    .stream()
                    .map(e -> new Greeter(e.getKey(),e.getValue()))
                    .forEach(Greeter::execute);

        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            LOG.warn("Error while greeting", t);
        }
    }

    @RequiredArgsConstructor
    private class Greeter {

        @NonNull
        private final ChannelInfo channelInfo;

        @NonNull
        private final ImmutableSet<User> greeters;

        private void execute() {
            final ChannelIO channelIO = io.forChannelInfo(channelInfo);
            formGreetings().forEach(channelIO::print);
        }

        @NonNull
        private ImmutableList<String> formGreetings() {
            final ImmutableList.Builder<String> messageBuilder = ImmutableList.builder();

            final Set<User> toProcess = new HashSet<>(greeters);

            {
                //special messages
                for (User greeter : greeters) {
                    formSpecialMessage(greeter)
                            .ifPresent(msg -> {
                                messageBuilder.add(msg);
                                toProcess.remove(greeter);
                            });
                }
            }

            if (!toProcess.isEmpty()) {
                //generic message
                final String genericMessage = toProcess.stream()
                                                       .map(User::getHighlightedUserName)
                                                       .collect(Collectors.joining(", ", "Hello ", " !"));
                messageBuilder.add(genericMessage);
            }

            return messageBuilder.build();
        }

        //TODO put that in a specific class
        private Optional<String> formSpecialMessage(@NonNull User user) {
            final String userId = user.getUserId();
            if (channelInfo.isOwnedBy(user)) {
                return Optional.of("Salut Chef !!!");
            }
            return Optional.ofNullable(Holder.SPECIAL_MESSAGES.get(userId))
                           .map(f -> f.f(user));
        }
    }

    private static class Holder {

        private static final ImmutableMap<String, Function1<? super User, String>> SPECIAL_MESSAGES =
                ImmutableMap.<String, Function1<? super User, String>>builder()
                        .put("ghostcatfr", u -> "Bonjour " + u.getHighlightedUserName() + " ! Est-ce que tu as passé une bonne journée ?")
                        .build();
    }

}
