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

    private final @NonNull ConnectionState connectionState;

    /**
     * The enabled/disabled state of the actions.
     */
    private final @NonNull ActionState actionState;

    /**
     * list of styles and current style
     */
    private final @NonNull StyleState styleState;

    /**
     * information regarding the server
     */
    private final @NonNull Configuration configuration;

    /**
     * The main view
     */
    private final @NonNull Class<? extends FXView> fxViewType;

}
