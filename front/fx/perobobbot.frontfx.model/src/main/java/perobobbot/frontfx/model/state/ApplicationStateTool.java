package perobobbot.frontfx.model.state;

import lombok.NonNull;
import perobobbot.action.Action;
import perobobbot.frontfx.model.view.FXView;

public interface ApplicationStateTool {

    boolean isEnabled(@NonNull Class<? extends Action<?, ?>> actionType);

    boolean isDisabled(@NonNull Class<? extends Action<?, ?>> actionType);

    @NonNull Class<? extends FXView> getMainFXView();

    @NonNull StyleState getStyleState();

    @NonNull String getServerBaseURL();

    @NonNull boolean isAuthenticated();
}
