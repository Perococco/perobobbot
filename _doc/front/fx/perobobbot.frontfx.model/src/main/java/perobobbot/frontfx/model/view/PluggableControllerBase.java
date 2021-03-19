package perobobbot.frontfx.model.view;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.action.ActionExecutor;
import perobobbot.frontfx.model.FXApplicationIdentity;
import perobobbot.frontfx.model.state.ApplicationStateTool;
import perobobbot.lang.SubscriptionHolder;

@RequiredArgsConstructor
public abstract class PluggableControllerBase implements PluggableController, ActionExecutor {

    private final @NonNull FXApplicationIdentity applicationIdentity;

    @Delegate
    private final @NonNull ActionExecutor actionExecutor;

    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();


    @Override
    public void onShowing() {
        subscriptionHolder.replaceWith(() -> applicationIdentity.addListenerAndCall(this::onApplicationStateChanged));
    }

    @Override
    public void onHiding() {
        subscriptionHolder.unsubscribe();
    }

    protected abstract void onApplicationStateChanged(@NonNull ApplicationStateTool tool);
}
