package perobobbot.server.config.extension;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import perobobbot.data.service.BotService;
import perobobbot.data.service.SecuredService;
import perobobbot.lang.ApplicationCloser;
import perobobbot.lang.Instants;
import perobobbot.lang.StandardInputProvider;
import perobobbot.plugin.PlatformChatPlugin;

@RequiredArgsConstructor
@Getter
@Component
public class ChatPlatformPluginParameters implements PlatformChatPlugin.Parameters {

    private final @NonNull Instants instants;
    private final @SecuredService
    @NonNull BotService botService;
    private final @NonNull StandardInputProvider standardInputProvider;
    private final @NonNull ApplicationCloser applicationCloser;
}
