package perobobbot.frontfx.model.state.mutation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.frontfx.model.state.ApplicationState;
import perobobbot.frontfx.model.view.FXView;
import perobobbot.lang.Mutation;

@RequiredArgsConstructor
public class ChangeView implements Mutation<ApplicationState> {

    private final @NonNull Class<? extends FXView> fxView;

    @Override
    public @NonNull ApplicationState mutate(@NonNull ApplicationState state) {
        if (state.getFxViewType() == fxView) {
            return state;
        }
        return state.toBuilder().fxViewType(fxView).build();
    }
}
