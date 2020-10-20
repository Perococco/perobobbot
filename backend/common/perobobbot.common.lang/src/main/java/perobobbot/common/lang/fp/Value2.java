package perobobbot.common.lang.fp;

import lombok.NonNull;
import lombok.Value;

import java.util.Map;

@Value
public class Value2<A,B> {

    @NonNull
    public static <A,B> Value2<A,B> of(@NonNull Map.Entry<A,B> entry) {
        return new Value2<>(entry.getKey(),entry.getValue());
    }

    @NonNull
    public static <A,B> Value2<A,B> of(@NonNull A a, @NonNull B b) {
        return new Value2<>(a,b);
    }

    @NonNull A a;

    @NonNull B b;

    @NonNull
    public <C,D> Value2<C,D> map(
            @NonNull Function1<? super A, ? extends C> aMapper,
            @NonNull Function1<? super B, ? extends D> bMapper) {
        return of(aMapper.f(a),bMapper.f(b));
    }

    @NonNull
    public <C> Value2<C,B> mapA(
            @NonNull Function1<? super A, ? extends C> aMapper) {
        return of(aMapper.f(a),b);
    }

    @NonNull
    public <C> Value2<A,C> mapB(
            @NonNull Function1<? super B, ? extends C> bMapper) {
        return of(a, bMapper.f(b));
    }

    @NonNull
    public Value2<B,A> swap() {
        return of(b,a);
    }
}
