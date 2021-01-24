package perobobbot.frontfx.model.view;

import lombok.NonNull;
import perococco.perobobbot.frontfx.model.view.PeroCachedFXView;

public interface CachedFXView extends FXView {

    static CachedFXView cached(@NonNull FXView fxView) {
        if (fxView instanceof CachedFXView) {
            return (CachedFXView) fxView;
        }
        return new PeroCachedFXView(fxView);
    }

    /**
     * Invalidate the cache
     */
    void invalidate();

}
