package perobobbot.frontfx.model.state;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.frontfx.model.view.FXView;

@RequiredArgsConstructor
@Builder(toBuilder = true)
@Getter
public class ApplicationState {

    /**
     * The enabled/disabled state of the actions.
     */
    private final @NonNull ActionState actionState;

    private final @NonNull StyleState styleState;

    private final @NonNull Class<? extends FXView> fxViewType;

}
