package perobobbot.extension;

import lombok.NonNull;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandRegistry;
import perobobbot.lang.Bot;
import perobobbot.messaging.ChatController;
import perobobbot.overlay.api.Overlay;
import perobobbot.sound.SoundResolver;

public interface ExtensionFactory {

    @NonNull Extension create(@NonNull Bot bot, @NonNull CommandRegistry commandRegistry);

    @NonNull String getExtensionName();

    /**
     * @return true if this extension should be auto started
     */
    default boolean isAutoStart() {
        return true;
    }


    interface Parameters {
        @NonNull ChatController getChatController();
        @NonNull Policy createPolicy(@NonNull AccessRule accessRule);
        @NonNull IO getIo();
        @NonNull Overlay getOverlay();
        @NonNull SoundResolver getSoundResolver();
    }

}