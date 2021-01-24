package perococco.perobobbot.frontfx.model.view;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.frontfx.model.view.FXView;
import perobobbot.frontfx.model.view.FXViewInstance;
import perobobbot.fx.FXLoader;

@RequiredArgsConstructor
public class FXViewWithLoader implements FXView {

    @NonNull
    private final FXLoader fxLoader;

    @Override
    public @NonNull FXViewInstance getViewInstance() {
        return new PeroFXViewInstance(fxLoader.load());
    }

}
