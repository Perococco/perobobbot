package perococco.perobobbot.frontfx.gui;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.action.Action;
import perobobbot.frontfx.model.state.ApplicationState;
import perobobbot.frontfx.model.state.ApplicationStateTool;
import perobobbot.frontfx.model.state.DashboardState;
import perobobbot.frontfx.model.state.StyleState;
import perobobbot.frontfx.model.view.EmptyFXView;
import perobobbot.frontfx.model.view.FXView;
import perobobbot.lang.Bot;
import perobobbot.security.com.RoleKind;
import perobobbot.security.com.SimpleUser;
import perococco.perobobbot.frontfx.gui.view.BotListView;
import perococco.perobobbot.frontfx.gui.view.LoginFXView;
import perococco.perobobbot.frontfx.gui.view.UserListView;

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
        if (this.isAuthenticated()) {
            return applicationState.getFxViewType();
        } else {
            return LoginFXView.class;
        }
    }

    @Override
    public @NonNull ImmutableList<SimpleUser> getUsers() {
        return applicationState.getDataState().getUsers();
    }

    @Override
    public @NonNull ImmutableList<Bot> getBots() {
        return applicationState.getDataState().getBots();
    }

    @Override
    public boolean canSeeUsers() {
        return applicationState.getUser()
                               .map(SimpleUser::getRoles)
                               .orElseGet(ImmutableSet::of)
                               .contains(RoleKind.ADMIN);
    }

    @Override
    public Class<? extends FXView> getDashboardMainView() {
        return switch (applicationState.getDashboardState().getViewType()) {
            case USERS -> UserListView.class;
            case BOTS -> BotListView.class;
            default -> EmptyFXView.class;
        };
    }

    @Override
    public @NonNull StyleState getStyleState() {
        return applicationState.getStyleState();
    }

    @Override
    public @NonNull String getServerBaseURL() {
        return applicationState.getConfiguration().getServerUri().toString();
    }

    @Override
    public @NonNull boolean isAuthenticated() {
        return applicationState.getUser().isPresent();
    }

    @Override
    public @NonNull String getUserLogin() {
        return applicationState.getUser().map(SimpleUser::getLogin).orElse("???");
    }

    @Override
    public @NonNull DashboardState getDashboardState() {
        return applicationState.getDashboardState();
    }
}
