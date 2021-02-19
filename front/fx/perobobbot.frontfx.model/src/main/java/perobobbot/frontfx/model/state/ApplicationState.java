package perobobbot.frontfx.model.state;

import lombok.*;
import perobobbot.frontfx.model.view.FXView;
import perobobbot.security.com.SimpleUser;

import java.util.Optional;

@RequiredArgsConstructor
@Builder(toBuilder = true)
@Getter
public class ApplicationState {

    @Getter(AccessLevel.NONE)
    private final SimpleUser user;

    private final @NonNull DataState dataState;

    private final @NonNull ConnectionState connectionState;

    /**
     * The enabled/disabled state of the actions.
     */
    private final @NonNull ActionState actionState;

    /**
     * list of styles and current style
     */
    private final @NonNull StyleState styleState;
    
    private final @NonNull DashboardState dashboardState;
    
    /**
     * information regarding the server
     */
    private final @NonNull Configuration configuration;

    /**
     * The main view
     */
    private final @NonNull Class<? extends FXView> fxViewType;

    public @NonNull Optional<SimpleUser> getUser() {
        return Optional.ofNullable(this.user);
    }

}
