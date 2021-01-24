package perobobbot.frontfx.action;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.action.ActionExecutor;
import perobobbot.action.ActionFilter;
import perobobbot.action.ActionManager;
import perobobbot.action.ActionProvider;
import perobobbot.frontfx.model.FXApplicationIdentity;
import perococco.perobobbot.frontfx.action.PeroActionExecutor;
import perococco.perobobbot.frontfx.action.PeroActionManager;

import java.util.concurrent.Executor;

@RequiredArgsConstructor
public class FxClientActionTool {

    @NonNull
    private final FXApplicationIdentity applicationIdentity;

    @NonNull
    private final Executor executor;

    @NonNull
    private final ActionProvider actionProvider;

    @NonNull
    private final ImmutableList<ActionFilter> actionFilters;


    private ActionManager actionManager;

    private ActionExecutor actionExecutor;

    public ActionManager getActionManager() {
        if (actionManager == null) {
            actionManager = new PeroActionManager(applicationIdentity, getActionExecutor());
        }
        return actionManager;
    }

    public ActionExecutor getActionExecutor() {
        if (actionExecutor == null) {
            actionExecutor = new PeroActionExecutor(executor, actionProvider, actionFilters);
        }
        return actionExecutor;
    }

}
