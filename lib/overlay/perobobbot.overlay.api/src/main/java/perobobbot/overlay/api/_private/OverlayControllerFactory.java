package perobobbot.overlay.api._private;

import lombok.NonNull;
import perobobbot.overlay.api.FrameRate;
import perobobbot.overlay.api.OverlayController;
import perobobbot.rendering.ScreenSize;
import perobobbot.sound.SoundManager;

public class OverlayControllerFactory implements OverlayController.Factory {

    private final @NonNull OverlayController.Factory delegate;

    public OverlayControllerFactory() {
        this.delegate = new FactoryFinder().find().orElseThrow(() -> new IllegalStateException("No OverlayController Factory could not be found"));
    }

    @Override
    public boolean isDefault() {
        return delegate.isDefault();
    }

    @Override
    public @NonNull String getImplementationName() {
        return delegate.getImplementationName();
    }

    @Override
    public @NonNull OverlayController create(@NonNull String name, @NonNull ScreenSize size, @NonNull FrameRate frameRate, @NonNull SoundManager soundManager) {
        return delegate.create(name, size, frameRate, soundManager);
    }
}
