package perobobbot.frontfx.action.list;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import perobobbot.frontfx.model.ApplicationIdentity;
import perobobbot.frontfx.model.state.ApplicationState;
import perobobbot.fx.StyleManager;

@Component
@RequiredArgsConstructor
public class ApplyTheme extends ActionNoResult<String> {

    private final @NonNull ApplicationIdentity applicationIdentity;
    private final @NonNull StyleManager styleManager;

    @Override
    protected void doExecute(@NonNull String themeName)  {
        applicationIdentity.mutate(state -> mutate(state,themeName))
        .thenAccept(s -> s.getStyleState().getSelectedTheme().ifPresent(styleManager::applyTheme));
    }

    private @NonNull ApplicationState mutate(@NonNull ApplicationState state, @NonNull String themeName) {
        final var newStyleState = state.getStyleState().withNewSelectedTheme(themeName);
        if (newStyleState == state.getStyleState()) {
            return state;
        }
        return state.toBuilder().styleState(newStyleState).build();
    }
}
