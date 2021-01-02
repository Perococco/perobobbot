package perobobbot.extension;

import lombok.NonNull;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.chat.core.IO;
import perobobbot.messaging.ChatController;
import perobobbot.overlay.api.Overlay;
import perobobbot.sound.SoundResolver;

public interface ExtensionFactory {

    @NonNull String getExtensionName();

    @NonNull ExtensionInfo create();

    interface Parameters {
        @NonNull ChatController getChatController();
        @NonNull Policy createPolicy(@NonNull AccessRule accessRule);
        @NonNull IO getIo();
        @NonNull Overlay getOverlay();
        @NonNull SoundResolver getSoundResolver();
    }

}
