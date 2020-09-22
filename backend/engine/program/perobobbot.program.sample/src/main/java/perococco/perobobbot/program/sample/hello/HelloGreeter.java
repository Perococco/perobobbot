package perococco.perobobbot.program.sample.hello;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import perobobbot.common.lang.AsyncIdentity;
import perobobbot.common.lang.ThrowableTool;
import perobobbot.program.core.BackgroundTask;
import perobobbot.program.core.ExecutionIO;
import perococco.perobobbot.program.sample.hello.mutation.ClearGreetingIssuers;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
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
        future = BackgroundTask.TASK_EXECUTOR.scheduleWithFixedDelay(
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
            if (!issuers.isEmpty()) {
                greets(issuers);
            }
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            LOG.warn("Error while greeting", t);
        }
    }

    private void greets(@NonNull ImmutableSet<GreetingIssuer> issuers) {
        final Map<String, Set<GreetingIssuer>> issuersByChannel = issuers.stream()
                                                                         .collect(Collectors.groupingBy(
                                                                                 GreetingIssuer::getChannelId,
                                                                                 Collectors.toSet()
                                                                         ));

        issuersByChannel.forEach((c,g) -> {
            g.stream().findFirst().map(GreetingIssuer::getExecutionIO)
             .ifPresent(io -> {
                 final String userList = g.stream().map(GreetingIssuer::getHighlightedUserName).collect(Collectors.joining(", "));
                 io.print("Salut "+userList+ ".");
             });
        });

    }


}
