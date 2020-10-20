package perobobbot.common.lang.fp;

import lombok.NonNull;
import lombok.Value;

@Value
public class Value3<A,B,C> {

    @NonNull
    public static <A,B,C> Value3<A,B,C> flatten(@NonNull Value2<A,Value2<B,C>> value) {
        return Value3.of(value.getA(),value.getB().getA(),value.getB().getB());
    }

    @NonNull
    public static <A,B,C> Value3<A,B,C> of(@NonNull A a, @NonNull B b, @NonNull C c) {
        return new Value3<>(a, b,c);
    }

    @NonNull A a;

    @NonNull B b;

    @NonNull C c;

    @NonNull
    public <D,E,F> Value3<D,E,F> map(
            @NonNull Function1<? super A, ? extends D> aMapper,
            @NonNull Function1<? super B, ? extends E> bMapper,
            @NonNull Function1<? super C, ? extends F> cMapper
            ) {
        return of(aMapper.f(a),bMapper.f(b), cMapper.apply(c));
    }

    @NonNull
    public <D> Value3<A,D,C> mapB(@NonNull Function1<? super B, ? extends D> bMapper) {
        return of(a,bMapper.f(b),c);
    }

    @NonNull
    public <D> D use(@NonNull Function3<? super A, ? super B, ? super C, ? extends D> function) {
        return function.f(a,b,c);
    }
}
