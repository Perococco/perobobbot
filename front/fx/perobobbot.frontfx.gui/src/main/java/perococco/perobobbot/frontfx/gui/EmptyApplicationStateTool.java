package perococco.perobobbot.frontfx.gui;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.action.Action;
import perobobbot.frontfx.model.state.ApplicationStateTool;
import perobobbot.frontfx.model.state.StyleState;
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

    @Override
    public @NonNull StyleState getStyleState() {
        return StyleState.builder().nameOfSelectedTheme("").themes(ImmutableMap.of()).build();
    }

    @Override
    public @NonNull String getServerBaseURL() {
        return "";
    }

    @Override
    public @NonNull boolean isAuthenticated() {
        return false;
    }
}
