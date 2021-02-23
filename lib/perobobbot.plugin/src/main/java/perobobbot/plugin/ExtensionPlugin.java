package perobobbot.plugin;

import lombok.NonNull;
import perobobbot.chat.core.IO;
import perobobbot.lang.Bank;
import perobobbot.messaging.ChatController;
import perobobbot.overlay.api.Overlay;
import perobobbot.sound.SoundResolver;

public interface ExtensionPlugin extends Plugin {

    /**
     * @param parameters parameters provided by the server
     * @return the extension and some information about it
     */
    @NonNull ExtensionInfo create(@NonNull Parameters parameters);


    interface Parameters {

        @NonNull ChatController getChatController();

        @NonNull IO getIo();

        @NonNull Overlay getOverlay();

        @NonNull SoundResolver getSoundResolver();

        @NonNull Bank getBank();

    }


}
