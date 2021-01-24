package perobobbot.frontfx.model.state;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.action.Action;
import perobobbot.frontfx.model.view.FXView;

@RequiredArgsConstructor
public class BasicApplicationStateTool implements ApplicationStateTool {

    @NonNull
    private final ApplicationState applicationState;

    @Override
    public boolean isEnabled(@NonNull Class<? extends Action<?, ?>> actionType) {
        return applicationState.getActionState().isEnabled(actionType);
    }

    @Override
    public boolean isDisabled(@NonNull Class<? extends Action<?, ?>> actionType) {
        return applicationState.getActionState().isDisabled(actionType);
    }

    @Override
    public @NonNull Class<? extends FXView> getMainFXView() {
        return applicationState.getFxViewType();
    }
}
