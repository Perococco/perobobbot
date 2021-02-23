package perobobbot.server.config.extension;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import perobobbot.access.ExecutionManager;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.chat.core.IO;
import perobobbot.data.service.BotService;
import perobobbot.data.service.SecuredService;
import perobobbot.lang.ApplicationCloser;
import perobobbot.lang.Bank;
import perobobbot.lang.Instants;
import perobobbot.lang.StandardInputProvider;
import perobobbot.messaging.ChatController;
import perobobbot.overlay.api.Overlay;
import perobobbot.plugin.ChatPlatformPlugin;
import perobobbot.plugin.ExtensionPlugin;
import perobobbot.sound.SoundResolver;

@RequiredArgsConstructor
@Getter
@Component
public class ChatPlatformPluginParameters implements ChatPlatformPlugin.Parameters {

    private final @NonNull Instants instants;
    private final @SecuredService
    @NonNull BotService botService;
    private final @NonNull StandardInputProvider standardInputProvider;
    private final @NonNull ApplicationCloser applicationCloser;
}
