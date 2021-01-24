package perobobbot.frontfx.model.state;

import lombok.NonNull;
import perobobbot.action.Action;
import perobobbot.frontfx.model.view.EmptyFXView;
import perobobbot.frontfx.model.view.FXView;

public class EmptyApplicationStateTool implements ApplicationStateTool {

    private static final EmptyApplicationStateTool INSTANCE = new EmptyApplicationStateTool();

    public static ApplicationStateTool create() {
        return INSTANCE;
    }

    @Override
    public boolean isEnabled(@NonNull Class<? extends Action<?, ?>> actionType) {
        return false;
    }

    @Override
    public boolean isDisabled(@NonNull Class<? extends Action<?, ?>> actionType) {
        return true;
    }

    @Override
    public @NonNull Class<? extends FXView> getMainFXView() {
        return EmptyFXView.class;
    }
}
