package perobobbot.server.config.extension;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.access.PolicyManager;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandRegistry;
import perobobbot.extension.ExtensionFactory;
import perobobbot.messaging.ChatController;
import perobobbot.overlay.api.Overlay;
import perobobbot.sound.SoundResolver;

@RequiredArgsConstructor
@Getter
@Component
public class ExtensionFactoryParameters implements ExtensionFactory.Parameters {

    private final @NonNull IO io;
    private final @NonNull ChatController chatController;
    private final @NonNull CommandRegistry commandRegistry;
    private final @NonNull PolicyManager policyManager;
    private final @NonNull Overlay overlay;
    private final @NonNull SoundResolver soundResolver;


    @Override
    public @NonNull Policy createPolicy(@NonNull AccessRule accessRule) {
        return policyManager.createPolicy(accessRule);
    }
}
