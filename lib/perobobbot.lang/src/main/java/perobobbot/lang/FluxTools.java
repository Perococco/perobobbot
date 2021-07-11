package perobobbot.lang;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class FluxTools {

    private static final Scheduler SCHEDULER = Schedulers.boundedElastic();

    public static @NonNull <T> CompletionStage<ImmutableList<T>> toCompletionStage(@NonNull Flux<T> flux) {
        final var future = new CompletableFuture<ImmutableList<T>>();
        setToCompletableFuture(flux, future);
        return future;
    }

    public static @NonNull <T> CompletionStage<ImmutableList<T>> toCompletionStageAsync(@NonNull Flux<T> mono) {
        return toCompletionStage(mono.subscribeOn(SCHEDULER));
    }

    public static <T> void setToCompletableFuture(@NonNull Flux<T> flux, @NonNull CompletableFuture<ImmutableList<T>> completableFuture) {
        final var builder = ImmutableList.<T>builder();
        flux.subscribe(builder::add, completableFuture::completeExceptionally,() -> completableFuture.complete(builder.build()));
    }

    public static <T> void setToCompletableFutureAsync(@NonNull Flux<T> mono, @NonNull CompletableFuture<ImmutableList<T>> completableFuture) {
        setToCompletableFuture(mono.subscribeOn(SCHEDULER), completableFuture);
    }

}
