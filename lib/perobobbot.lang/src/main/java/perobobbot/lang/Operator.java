package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Function1;
import perococco.perobobbot.common.lang.SimpleOperator;

public interface Operator<S,T> extends GetterOnStates<S,T> {

    @NonNull S mutate(S state);

    static <S,T> @NonNull Operator<S,T> getter(@NonNull Function1<? super S, ? extends T> action) {
        return new SimpleOperator<>(s -> s, (oldState, newState) -> action.f(newState));
    }

}
