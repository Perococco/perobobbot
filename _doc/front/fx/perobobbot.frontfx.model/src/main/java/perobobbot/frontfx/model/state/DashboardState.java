package perobobbot.frontfx.model.state;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class DashboardState {

    public static @NonNull DashboardState initialState() {
        return new DashboardState(DashboardMainViewType.WELCOME);
    }

    /**
     * The type of the view in the dashboard main slot
     */
    @NonNull DashboardMainViewType viewType;

    public @NonNull DashboardState withNewViewType(DashboardMainViewType viewType) {
        if (viewType == this.viewType) {
            return this;
        }
        return toBuilder().viewType(viewType).build();
    }
}
