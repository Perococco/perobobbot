package perococco.perobobbot.frontfx.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.action.ActionBinder;
import perobobbot.action.ActionBinding;
import perobobbot.action.NilActionBinder;
import perobobbot.lang.Nil;

@RequiredArgsConstructor
public class PeroNilActionBinder implements NilActionBinder {

    @NonNull
    private final ActionBinder<Nil> delegate;

    @Override
    public @NonNull ActionBinding createBinding(@NonNull Object item) {
        return delegate.createBinding(item,Nil.NIL);
    }
}
