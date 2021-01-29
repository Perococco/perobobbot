package perobobbot.overlay.newtek;

import lombok.NonNull;
import perobobbot.overlay.api.FrameRate;
import perobobbot.overlay.api.OverlayController;
import perobobbot.rendering.ScreenSize;
import perobobbot.sound.SoundManager;

public class NewtekFactory implements OverlayController.Factory {

    @Override
    public @NonNull String getImplementationName() {
        return "newtek";
    }

    @Override
    public boolean isDefault() {
        return true;
    }

    @Override
    public @NonNull OverlayController create(@NonNull String name, @NonNull ScreenSize overlaySize, @NonNull FrameRate frameRate, @NonNull SoundManager soundManager) {
        return new NewtekOverlayController(name,overlaySize,frameRate,soundManager);
    }
}
