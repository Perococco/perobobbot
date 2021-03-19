package perobobbot.frontfx.model.view;

import lombok.NonNull;
import perobobbot.fx.FXLoader;
import perococco.perobobbot.frontfx.model.view.FXViewWithLoader;

public interface FXView {

    @NonNull
    static FXView create(@NonNull FXLoader fxLoader) {
        return new FXViewWithLoader(fxLoader);
    }

    @NonNull
    FXViewInstance getViewInstance();

    default CachedFXView cached() {
        return CachedFXView.cached(this);
    }
}
