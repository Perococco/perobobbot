package perococco.perobobbot.frontfx.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.action.*;
import perobobbot.frontfx.model.FXApplicationIdentity;
import perobobbot.lang.Nil;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
@Log4j2
public class PeroActionManager implements ActionManager {

    @NonNull
    private final FXApplicationIdentity fxApplicationIdentity;

    @NonNull
    private final ActionExecutor actionExecutor;


    @Override
    public @NonNull <P,R> ActionBinder<P> binder(@NonNull Launchable<P, R> launchable) {
        return new PeroActionBinder<>(fxApplicationIdentity, actionExecutor, launchable);
    }

    @Override
    public @NonNull <R> NilActionBinder nilBinder(@NonNull Launchable<Nil, R> launchable) {
        final ActionBinder<Nil> delegate = binder(launchable);
        return new PeroNilActionBinder(delegate);
    }

    @Override
    public @NonNull <P, R> ActionTicket<R> pushAction(@NonNull Launchable<P, R> launchable,
                                                      @NonNull P parameter) {
        final CompletionStage<R> completionStage = launchable.launch(actionExecutor, parameter);
        final ActionTicket<R> ticket = new PeroActionTicket<>(actionExecutor, completionStage);

        return ticket.whenComplete((n, t) -> {
            if (t != null) {
                logError(launchable, parameter, t);
            }
        });

    }

    private <P, R> void logError(@NonNull Launchable<P, R> launchable,
                                 @NonNull P parameter,
                                 @NonNull Throwable t) {
        LOG.error("An error occurred while executing Action {}", launchable.getInitialAction(), t);
    }

}
