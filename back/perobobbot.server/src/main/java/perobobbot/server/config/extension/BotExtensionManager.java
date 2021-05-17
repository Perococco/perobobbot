package perobobbot.server.config.extension;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import perobobbot.data.com.Extension;
import perobobbot.data.com.event.ExtensionEvent;
import perobobbot.data.service.ExtensionService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.extension.ExtensionManager;
import perobobbot.lang.GatewayChannels;

//@Component
@RequiredArgsConstructor
public class BotExtensionManager {

    private final @NonNull
    @UnsecuredService
    ExtensionService extensionService;

    private final @NonNull ExtensionManager extensionManager;

    @ServiceActivator(inputChannel = GatewayChannels.EVENT_MESSAGES)
    public void updateExtensionState(@NonNull ExtensionEvent event) {
        var activatedNames = extensionService.listAllExtensions()
                                             .stream()
                                             .filter(Extension::isActivated)
                                             .map(Extension::getName)
                                             .collect(ImmutableSet.toImmutableSet());
        extensionManager.enableExtensions(activatedNames);
    }

}
