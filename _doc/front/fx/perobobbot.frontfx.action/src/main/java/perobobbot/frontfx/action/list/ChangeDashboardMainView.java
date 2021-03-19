package perobobbot.frontfx.action.list;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import perobobbot.frontfx.model.ApplicationIdentity;
import perobobbot.frontfx.model.state.DashboardMainViewType;
import perobobbot.frontfx.model.state.mutation.ChangeDashboardView;

@Component
@RequiredArgsConstructor
public class ChangeDashboardMainView extends ActionNoResult<DashboardMainViewType> {

    private final @NonNull ApplicationIdentity applicationIdentity;

    @Override
    protected void doExecute(@NonNull DashboardMainViewType viewType) throws Throwable {
        applicationIdentity.mutate(new ChangeDashboardView(viewType));
    }
}
