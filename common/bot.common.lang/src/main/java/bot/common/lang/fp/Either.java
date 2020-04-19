package bot.common.lang.fp;

import lombok.NonNull;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public final class Either<L, R> {

    @NonNull
    public static <L, R> Either<L, R> left(@NonNull L left) {
        return new Either<>(left, null);
    }

    @NonNull
    public static <L, R> Either<L, R> right(@NonNull R right) {
        return new Either<>(null,right);
    }

    private final L left;

    private final R right;

    private Either(L left, R right) {
        assert (left == null) != (right == null) : "only one of left or right must be null";
        this.left = left;
        this.right = right;
    }

    @NonNull
    public Optional<L> left() {
        return Optional.ofNullable(left);
    }

    @NonNull
    public Optional<R> right() {
        return Optional.ofNullable(right);
    }

    public boolean isLeft() {
        return left != null;
    }

    public boolean isRight() {
        return right != null;
    }

    public void accept(
            @NonNull Consumer<? super L> leftConsumer,
            @NonNull Consumer<? super R> rightConsumer
    ) {
        if (left != null) {
            leftConsumer.accept(left);
        } else {
            rightConsumer.accept(right);
        }
    }

    @NonNull
    public <M> M merge(
            @NonNull Function1<? super L, ? extends M> leftFunction,
            @NonNull Function1<? super R, ? extends M> rightFunction
    ) {
        if (left != null) {
            return leftFunction.f(left);
        }
        return rightFunction.f(right);
    }

    @NonNull
    public <M, T extends Throwable> M tryMerge(
            @NonNull Try1<? super L, ? extends M, T> leftFunction,
            @NonNull Try1<? super R, ? extends M, T> rightFunction
    ) throws T {
        if (left != null) {
            return leftFunction.f(left);
        }
        return rightFunction.f(right);
    }

    @NonNull
    public <M,S> Either<M,S> map(
            @NonNull Function1<? super L, ? extends M> mapLeft,
            @NonNull Function1<? super R, ? extends S> mapRight
    ) {
        return merge(l -> Either.left(mapLeft.f(l)), r -> Either.right(mapRight.f(r)));
    }

    @NonNull
    public <M> Either<M,R> mapLeft(@NonNull Function1<? super L, ? extends M> mapper) {
        if (left != null) {
            return Either.left(mapper.f(left));
        }
        //noinspection unchecked
        return (Either<M,R>)this;
    }

    @NonNull
    public <S> Either<L,S> mapRight(@NonNull Function1<? super R, ? extends S> mapper) {
        if (right != null) {
            return Either.right(mapper.f(right));
        }
        //noinspection unchecked
        return (Either<L,S>)this;
    }

}
