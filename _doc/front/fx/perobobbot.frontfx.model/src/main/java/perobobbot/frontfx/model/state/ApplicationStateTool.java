package perobobbot.frontfx.model.state;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.action.Action;
import perobobbot.frontfx.model.view.FXView;
import perobobbot.lang.Bot;
import perobobbot.security.com.SimpleUser;

public interface ApplicationStateTool {

    boolean isEnabled(@NonNull Class<? extends Action<?, ?>> actionType);

    boolean isDisabled(@NonNull Class<? extends Action<?, ?>> actionType);

    @NonNull Class<? extends FXView> getMainFXView();

    @NonNull StyleState getStyleState();

    @NonNull DashboardState getDashboardState();

    @NonNull String getServerBaseURL();

    @NonNull boolean isAuthenticated();

    @NonNull String getUserLogin();

    @NonNull ImmutableList<Bot> getBots();

    boolean canSeeUsers();

    Class<? extends FXView> getDashboardMainView();

    @NonNull ImmutableList<SimpleUser> getUsers();
}
