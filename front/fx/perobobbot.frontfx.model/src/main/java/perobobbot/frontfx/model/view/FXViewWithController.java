package perobobbot.frontfx.model.view;

import lombok.NonNull;
import perobobbot.fx.FXLoader;
import perobobbot.fx.FXLoaderFactory;

public class FXViewWithController implements FXView {

    @NonNull
    private final FXLoader fxLoader;

    public FXViewWithController(@NonNull FXLoaderFactory fxLoaderFactory, @NonNull Class<?> controllerClass) {
        this.fxLoader = fxLoaderFactory.create(controllerClass);
    }

    @Override
    public @NonNull FXViewInstance getViewInstance() {
        return FXViewInstance.with(fxLoader.load());
    }
}
