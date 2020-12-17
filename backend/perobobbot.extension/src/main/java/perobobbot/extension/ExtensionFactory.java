package perobobbot.extension;

import lombok.NonNull;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandRegistry;
import perobobbot.messaging.ChatController;
import perobobbot.overlay.api.Overlay;

public interface ExtensionFactory {

    @NonNull Extension create(@NonNull String userId);

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
        @NonNull CommandRegistry getCommandRegistry();
        @NonNull Overlay getOverlay();
    }

}
