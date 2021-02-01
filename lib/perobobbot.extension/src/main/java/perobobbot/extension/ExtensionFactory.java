package perobobbot.extension;

import lombok.NonNull;
import perobobbot.chat.core.IO;
import perobobbot.lang.Bank;
import perobobbot.messaging.ChatController;
import perobobbot.overlay.api.Overlay;
import perobobbot.sound.SoundResolver;

public interface ExtensionFactory {

    @NonNull String getExtensionName();

    @NonNull ExtensionInfo create();

    interface Parameters {
        @NonNull ChatController getChatController();
        @NonNull IO getIo();
        @NonNull Overlay getOverlay();
        @NonNull SoundResolver getSoundResolver();
        @NonNull Bank getBank();
    }

}
