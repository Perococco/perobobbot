package perobobbot.frontfx.model.state.mutation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.frontfx.model.state.ApplicationState;
import perobobbot.frontfx.model.state.DashboardMainViewType;
import perobobbot.lang.Mutation;

import java.net.MalformedURLException;

@RequiredArgsConstructor
public class ChangeDashboardView implements Mutation<ApplicationState> {

    private final @NonNull DashboardMainViewType viewType;

    @Override
    public @NonNull ApplicationState mutate(@NonNull ApplicationState state) {
        final var dashboardState = state.getDashboardState().withNewViewType(viewType);
        if (state.getDashboardState() == dashboardState) {
            return state;
        }
        return state.toBuilder().dashboardState(dashboardState).build();
    }
}
