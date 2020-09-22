package perococco.perobobbot.program.sample.hello;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import perobobbot.common.lang.AsyncIdentity;
import perobobbot.common.lang.ImmutableEntry;
import perobobbot.common.lang.MapTool;
import perobobbot.common.lang.ThrowableTool;
import perobobbot.program.core.BackgroundTask;
import perobobbot.program.core.ExecutionIO;
import perococco.perobobbot.program.sample.hello.mutation.ClearGreetingIssuers;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
public class HelloGreeter implements BackgroundTask {

    private final AsyncIdentity<HelloState> state;

    private ScheduledFuture<?> future;

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
            final ImmutableSet<GreetingIssuer> issuers;
            issuers = state.mutateAndGet(ClearGreetingIssuers.create(), (o, n) -> o.getGreetingIssuers())
                           .toCompletableFuture()
                           .get();

            Greeter.execute(issuers);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            LOG.warn("Error while greeting", t);
        }
    }

    @RequiredArgsConstructor
    private static class Greeter {

        public static void execute(@NonNull ImmutableSet<GreetingIssuer> issuers) {
            if (issuers.isEmpty()) {
                return;
            }
            new Greeter(issuers).execute();
        }


        @NonNull
        private final ImmutableSet<GreetingIssuer> issuers;

        private Map<String, Set<GreetingIssuer>> issuersByChannelId;

        private Map<String, ExecutionIO> ioByChannelId;

        private void execute() {
            this.groupIssuersByChannelId();
            this.getExecutionIOByChannelId();
            this.warnOnAllChannels();
        }

        private void groupIssuersByChannelId() {
            this.issuersByChannelId = this.issuers.stream()
                                                  .collect(Collectors.groupingBy(
                                                          GreetingIssuer::getChannelId,
                                                          Collectors.toSet()
                                                           )
                                                  );
        }

        private void getExecutionIOByChannelId() {
            ioByChannelId = issuersByChannelId.entrySet()
                                              .stream()
                                              .map(this::selectFirst)
                                              .flatMap(Optional::stream)
                                              .collect(MapTool.entryCollector());
        }

        @NonNull
        private Optional<Map.Entry<String, ExecutionIO>> selectFirst(@NonNull Map.Entry<String, Set<GreetingIssuer>> entry) {
            return entry.getValue()
                        .stream()
                        .findFirst()
                        .map(GreetingIssuer::getExecutionIO)
                        .map(io -> new ImmutableEntry<>(entry.getKey(), io));
        }


        @NonNull
        private String getUserList(String channelId) {
            return issuersByChannelId.get(channelId)
                                     .stream()
                                     .map(GreetingIssuer::getHighlightedUserName)
                                     .collect(Collectors.joining(","));
        }

        private void warnOnAllChannels() {
            for (Map.Entry<String, ExecutionIO> entry : ioByChannelId.entrySet()) {
                final String channelId = entry.getKey();
                final ExecutionIO io = entry.getValue();
                final String userList = getUserList(channelId);

                formMessage(userList).ifPresent(io::print);
            }
        }

        private Optional<String> formMessage(String userList) {
            if (userList.isBlank()) {
                return Optional.empty();
            }
            return Optional.of("Salut %s !".formatted(userList));
        }
    }


    private void greets(@NonNull ImmutableSet<GreetingIssuer> issuers) {
        final Map<String, Set<GreetingIssuer>> issuersByChannel = issuers.stream()
                                                                         .collect(Collectors.groupingBy(
                                                                                 GreetingIssuer::getChannelId,
                                                                                 Collectors.toSet()
                                                                         ));

        issuersByChannel.forEach((c, g) -> {
            g.stream().findFirst().map(GreetingIssuer::getExecutionIO)
             .ifPresent(io -> {
                 final String userList = g.stream().map(GreetingIssuer::getHighlightedUserName).collect(Collectors.joining(", "));
                 io.print("Salut " + userList + ".");
             });
        });

    }


}
