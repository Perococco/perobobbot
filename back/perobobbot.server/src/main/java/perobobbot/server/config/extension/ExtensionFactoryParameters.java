package perobobbot.server.config.extension;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import perobobbot.access.ExecutionManager;
import perobobbot.chat.core.IO;
import perobobbot.extension.ExtensionFactory;
import perobobbot.extension.StoreController;
import perobobbot.messaging.ChatController;
import perobobbot.overlay.api.Overlay;
import perobobbot.sound.SoundResolver;

@RequiredArgsConstructor
@Getter
@Component
public class ExtensionFactoryParameters implements ExtensionFactory.Parameters {

    private final @NonNull IO io;
    private final @NonNull ChatController chatController;
    private final @NonNull ExecutionManager executionManager;
    private final @NonNull Overlay overlay;
    private final @NonNull SoundResolver soundResolver;
    private final @NonNull StoreController storeController;
}
