package perobobbot.server.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.lang.Subscription;
import perobobbot.overlay.api.FrameRate;
import perobobbot.overlay.api.OverlayClient;
import perobobbot.overlay.api.OverlayController;
import perobobbot.plugin.PluginService;
import perobobbot.rendering.ScreenSize;
import perobobbot.sound.SoundManager;
import perobobbot.timeline.Property;

import java.net.URL;
import java.util.UUID;

@RequiredArgsConstructor
public class SpringOverlayControllerWrapper implements OverlayController, PluginService {

    private final @NonNull OverlayController delegate;

    @Override
    public @NonNull Subscription addClient(@NonNull OverlayClient client) {
        return delegate.addClient(client);
    }

    @Override
    public ScreenSize getOverlaySize() {
        return delegate.getOverlaySize();
    }

    @Override
    public @NonNull FrameRate getFrameRate() {
        return delegate.getFrameRate();
    }

    @NonNull
    public static OverlayController create(@NonNull String name, @NonNull ScreenSize size, @NonNull SoundManager soundManager) {
        return OverlayController.create(name, size, soundManager);
    }

    public static OverlayController create(@NonNull String name, @NonNull ScreenSize size, @NonNull FrameRate frameRate, @NonNull SoundManager soundManager) {
        return OverlayController.create(name, size, frameRate, soundManager);
    }

    @Override
    @NonNull
    public String getImplementationName() {
        return delegate.getImplementationName();
    }

    @Override
    public void start() {
        delegate.start();
    }

    @Override
    public void stop() {
        delegate.stop();
    }

    @Override
    public @NonNull UUID registerSoundResource(@NonNull URL soundPath) {
        return delegate.registerSoundResource(soundPath);
    }

    @Override
    public void unregisterSoundResource(@NonNull UUID uuid) {
        delegate.unregisterSoundResource(uuid);
    }

    @Override
    public @NonNull Property createProperty() {
        return delegate.createProperty();
    }
}
