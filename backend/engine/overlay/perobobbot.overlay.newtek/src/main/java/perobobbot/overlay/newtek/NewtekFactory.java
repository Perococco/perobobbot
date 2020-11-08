package perobobbot.overlay.newtek;

import lombok.NonNull;
import perobobbot.common.sound.SoundManager;
import perobobbot.overlay.api.FrameRate;
import perobobbot.overlay.api.OverlayController;

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
    public @NonNull OverlayController create(@NonNull String name, int width, int height, @NonNull FrameRate frameRate, @NonNull SoundManager soundManager) {
        return new NewtekOverlayController(name,width,height,frameRate,soundManager);
    }
}
