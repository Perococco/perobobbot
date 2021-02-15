package perococco.perobobbot.frontfx.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.action.ActionBinder;
import perobobbot.action.ActionBinding;
import perobobbot.action.ActionExecutor;
import perobobbot.action.Launchable;
import perobobbot.frontfx.model.FXApplicationIdentity;
import perobobbot.lang.ThrowableTool;
import perobobbot.lang.fp.Consumer0;
import perobobbot.lang.fp.Function0;

import java.util.Optional;

@RequiredArgsConstructor
public class PeroActionBinder<P,R> implements ActionBinder<P> {

    @NonNull
    private final FXApplicationIdentity fxApplicationIdentity;

    @NonNull
    private final ActionExecutor actionExecutor;

    @NonNull
    private final Launchable<P,R> launchable;

    private final ItemInfoProvider itemInfoProvider = new ItemInfoProvider();

    @Override
    public @NonNull ActionBinding createBinding(@NonNull Object item,
                                                @NonNull Function0<? extends Optional<? extends P>> parameterSupplier) {
        final ItemActionInfo itemActionInfo = itemInfoProvider.createInfo(item);
        final Consumer0 executable = createExecutable(parameterSupplier);
        return new PeroActionBinding(itemActionInfo, executable, fxApplicationIdentity.disabledProperty(launchable.getInitialAction()));
    }

    @NonNull
    private Consumer0 createExecutable(@NonNull Function0<? extends Optional<? extends P>> parameterSupplier) {
        return () -> {
            try {
                parameterSupplier.get().ifPresent(p -> launchable.launch(actionExecutor, p));
            } catch (Throwable t) {
                ThrowableTool.interruptThreadIfCausedByInterruption(t);
            }
        };
    }


}
