package perobbot.twitch.oauth;

import lombok.NonNull;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class MonoTools {

    private static final Scheduler SCHEDULER = Schedulers.boundedElastic();

    public static @NonNull <T> CompletionStage<T> toCompletionStage(@NonNull Mono<T> mono) {
        final var future = new CompletableFuture<T>();
        setToCompletableFuture(mono,future);
        return future;
    }

    public static @NonNull <T> CompletionStage<T> toCompletionStageAsync(@NonNull Mono<T> mono) {
        return toCompletionStage(mono.subscribeOn(SCHEDULER));
    }

    public static <T> void setToCompletableFuture(@NonNull Mono<T> mono, @NonNull CompletableFuture<T> completableFuture) {
        mono.subscribe(completableFuture::complete,completableFuture::completeExceptionally);
    }

    public static <T> void setToCompletableFutureAsync(@NonNull Mono<T> mono, @NonNull CompletableFuture<T> completableFuture) {
        setToCompletableFuture(mono.subscribeOn(SCHEDULER),completableFuture);
    }

}
