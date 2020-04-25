package bot.common.lang.fp;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TryResult<T extends Throwable, R> {

    @NonNull
    public static <R> TryResult<Throwable, R> withStageResult(R result, Throwable error) {
        if (error != null) {
            return TryResult.failure(error);
        }
        return TryResult.success(result);
    }

    @NonNull
    public static <T extends Throwable, R> TryResult<T, R> success(@NonNull R result) {
        return new TryResult<>(Either.right(result));
    }

    @NonNull
    public static <T extends Throwable, R> TryResult<T, R> failure(@NonNull T error) {
        return new TryResult<>(Either.left(error));
    }

    @NonNull
    private final Either<T, R> either;

    @NonNull
    public R get() throws T {
        return either.tryMerge(t -> {
            throw t;
        }, r -> r);
    }

    @NonNull
    public Optional<R> success() {
        return either.right();
    }

    @NonNull
    public Optional<T> failure() {
        return either.left();
    }

    public boolean isSuccess() {
        return either.isRight();
    }

    public boolean isFailure() {
        return either.isLeft();
    }

    @NonNull
    public <S> TryResult<T, S> map(@NonNull Function1<? super R, ? extends S> mapper) {
        return new TryResult<>(either.mapRight(mapper));
    }

    public void accept(@NonNull Consumer<? super T> leftConsumer, @NonNull Consumer<? super R> rightConsumer) {
        either.accept(leftConsumer, rightConsumer);
    }

    @NonNull
    public <M> M merge(@NonNull Function1<? super T, ? extends M> leftFunction, @NonNull Function1<? super R, ? extends M> rightFunction) {
        return either.merge(leftFunction, rightFunction);
    }

    @NonNull
    public <M, T1 extends Throwable> M tryMerge(@NonNull Try1<? super T, ? extends M, T1> leftFunction, @NonNull Try1<? super R, ? extends M, T1> rightFunction) throws T1 {
        return either.tryMerge(leftFunction, rightFunction);
    }

}
