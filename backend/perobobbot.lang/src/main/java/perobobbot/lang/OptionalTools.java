package perobobbot.lang;

import lombok.NonNull;
import lombok.experimental.ExtensionMethod;
import perobobbot.lang.fp.Consumer2;
import perobobbot.lang.fp.Function2;
import perobobbot.lang.fp.Value2;

import java.awt.image.AffineTransformOp;
import java.util.Optional;

public class OptionalTools {


    public static <A,B> void accept(@NonNull Optional<A> a, @NonNull Optional<B> b, @NonNull Consumer2<? super A, ? super B> ifPresent) {
        if (a.isPresent() && b.isPresent()) {
            ifPresent.accept(a.get(),b.get());
        }
    }

    public static <A,B> @NonNull Optional<Value2<A,B>> combine(@NonNull Optional<A> a, @NonNull Optional<B> b) {
        if (a.isPresent() && b.isPresent()) {
            return Optional.of(Value2.of(a.get(), b.get()));
        }
        return Optional.empty();
    }

    public static <A,B,C> @NonNull Optional<C> map(@NonNull Optional<A> a, @NonNull Optional<B> b, @NonNull Function2<? super A, ? super B, ? extends C> mapper) {
        if (a.isPresent() && b.isPresent()) {
            return Optional.of(mapper.apply(a.get(), b.get()));
        }
        return Optional.empty();
    }

    public static <A,B,C> @NonNull Optional<C> flatMap(@NonNull Optional<A> a, @NonNull Optional<B> b, @NonNull Function2<? super A, ? super B, ? extends Optional<C>> mapper) {
        if (a.isPresent() && b.isPresent()) {
            return mapper.apply(a.get(), b.get());
        }
        return Optional.empty();
    }
}
