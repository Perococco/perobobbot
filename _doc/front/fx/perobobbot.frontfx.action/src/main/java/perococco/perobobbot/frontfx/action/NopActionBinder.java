package perococco.perobobbot.frontfx.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.action.Action;
import perobobbot.action.ActionBinder;
import perobobbot.action.ActionBinding;
import perobobbot.lang.fp.Function0;

import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
public class NopActionBinder<P> implements ActionBinder<P> {

    @NonNull
    private final Class<? extends Action<P, ?>> actionType;

    @Override
    public @NonNull ActionBinding createBinding(@NonNull Object item,
                                                @NonNull Function0<? extends Optional<? extends P>> parameterSupplier) {
        LOG.error("Cannot bind action {} to item {}", actionType, item.getClass());
        ;
        return new NopActionBinding();
    }
}
