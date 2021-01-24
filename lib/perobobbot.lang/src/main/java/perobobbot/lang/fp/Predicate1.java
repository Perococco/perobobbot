package perobobbot.lang.fp;

import lombok.NonNull;

import java.util.function.Predicate;

public interface Predicate1<A> extends Predicate<A> {

    @NonNull
    static <A,R> Predicate1<A> toPredicate1(@NonNull Predicate<A> predicate) {
        if (predicate instanceof Predicate1) {
            return (Predicate1<A>)predicate;
        }
        return predicate::test;
    }


    @NonNull
    boolean test(@NonNull A a);

    @Override
    default Predicate1<A> negate() {
        return a -> !test(a);
    }

    @Override
    default Predicate1<A> and(Predicate<? super A> other) {
        return a -> test(a) && other.test(a);
    }

    @Override
    default Predicate1<A> or(Predicate<? super A> other) {
        return a -> test(a) || other.test(a);
    }
}
