package perococco.perobobbot.frontfx.model.view;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.frontfx.model.view.CachedFXView;
import perobobbot.frontfx.model.view.FXView;
import perobobbot.frontfx.model.view.FXViewInstance;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Optional;

@RequiredArgsConstructor
public class PeroCachedFXView implements CachedFXView {

    @NonNull
    private final FXView reference;

    private Reference<FXViewInstance> cache = null;

    @Override
    public void invalidate() {
        cache = null;
    }

    @Override
    public @NonNull FXViewInstance getViewInstance() {
        return Optional.ofNullable(cache)
                       .map(Reference::get)
                       .orElseGet(this::getNewInstance);
    }

    public FXViewInstance getNewInstance() {
        final FXViewInstance newValue = reference.getViewInstance();
        this.cache = new SoftReference<>(newValue);
        return newValue;
    }
}
