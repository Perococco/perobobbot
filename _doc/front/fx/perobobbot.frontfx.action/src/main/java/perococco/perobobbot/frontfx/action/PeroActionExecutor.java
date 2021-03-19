package perococco.perobobbot.frontfx.action;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.action.Action;
import perobobbot.action.ActionExecutor;
import perobobbot.action.ActionFilter;
import perobobbot.action.ActionProvider;
import perobobbot.frontfx.model.SystemProperties;
import perobobbot.lang.ThrowableTool;
import perobobbot.lang.fp.TryResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

@RequiredArgsConstructor
@Log4j2
public class PeroActionExecutor implements ActionExecutor {

    @NonNull
    private final Executor executor;

    @NonNull
    private final ActionProvider actionProvider;

    @NonNull
    private final ImmutableList<ActionFilter> actionFilters;


    @Override
    public @NonNull <P, R> CompletionStage<R> pushAction(
            @NonNull Class<? extends Action<? super P, ? extends R>> actionClass,
            @NonNull P parameter) {
        try {
            final Action<? super P, ? extends R> action = actionProvider.getAction(actionClass);
            return this.pushAction(action, parameter);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            return CompletableFuture.failedFuture(t);
        }
    }

    public <P, R> @NonNull CompletionStage<R> pushAction(@NonNull Action<? super P, ? extends R> action,
                                                         @NonNull P parameter) {
        final CompletableFuture<R> result = new CompletableFuture<>();
        final ActionItem<P, R> actionItem = new ActionItem<>(action, parameter, result);
        try {
            final Runnable runnable = () -> this.performAction(actionItem);
            executor.execute(runnable);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            actionItem.completeExceptionally(t);
        }
        return result;
    }

    private <P, R> void performAction(@NonNull ActionItem<P, R> ticket) {
        final Runnable execution;
        if (SystemProperties.TRACE_ACTIONS) {
            execution = wrapWithTrace(ticket,createExecution(ticket));
        } else {
            execution = createExecution(ticket);
        }
        ticket.executeIfNotCompleted(execution);
    }

    private Runnable wrapWithTrace(@NonNull ActionItem<?,?> ticket, @NonNull Runnable execution) {
        return () -> {
            final long start = System.nanoTime();
            final String actionName = ticket.getAction().getClass().getSimpleName();
            System.out.format("## %7s action '%s'%n","launch",actionName);
            try {
                execution.run();
                final long last = System.nanoTime() - start;
                System.out.format("## %7s action '%s' : %.3f ms %n","done",actionName,last/1e6);
            } catch (Throwable t){
                System.out.format("## %7s action '%s' : %s%n","fail",actionName,t.getMessage());
            }
        };
    }

    private <P, R> Runnable createExecution(@NonNull ActionItem<P, R> ticket) {
        return () -> {
            final P parameter = ticket.getParameter();
            Action<? super P, ? extends R> effectiveAction = ticket.getAction();
            try {
                for (ActionFilter actionFilter : actionFilters) {
                    effectiveAction = actionFilter.preProcessAction(effectiveAction, parameter);
                }

                final R result = effectiveAction.execute(parameter);

                postProcess(effectiveAction, TryResult.success(result));
                ticket.completeWith(result);

            } catch (Throwable t) {
                LOG.warn("Action {} failed : {}", ticket.getAction().getClass().getSimpleName(), t.getMessage());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Stacktrace ", t);
                }
                ThrowableTool.interruptThreadIfCausedByInterruption(t);
                postProcess(effectiveAction, TryResult.failure(t));
                ticket.completeExceptionally(t);
            }
        };
    }

    private <P, R> void postProcess(@NonNull Action<? super P, ? extends R> action,
                                    @NonNull TryResult<Throwable,R> result) {
        for (ActionFilter actionFilter : actionFilters.reverse()) {
            actionFilter.postProcessAction(action, result);
        }
    }
}
