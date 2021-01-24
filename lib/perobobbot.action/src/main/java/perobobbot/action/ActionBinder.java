package perobobbot.action;

import lombok.NonNull;
import perobobbot.lang.fp.Function0;

import java.util.Optional;

public interface ActionBinder<P> {

    @NonNull
    ActionBinding createBinding(@NonNull Object item, @NonNull Function0<? extends Optional<? extends P>> parameterSupplier);

    @NonNull
    default ActionBinding createBinding(@NonNull Object item, @NonNull P parameter) {
        return createBinding(item,Function0.cons(Optional.of(parameter)));
    }

    @NonNull
    default ActionBinding bind(@NonNull Object item, @NonNull Function0<? extends Optional<? extends P>> parameterSupplier) {
        final ActionBinding binding = createBinding(item, parameterSupplier);
        binding.bind();
        return binding;
    }

    @NonNull
    default ActionBinding bind(@NonNull Object item, @NonNull P parameter) {
        return bind(item,Function0.cons(Optional.of(parameter)));
    }

}
