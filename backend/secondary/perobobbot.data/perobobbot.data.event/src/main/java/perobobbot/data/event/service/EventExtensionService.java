package perobobbot.data.event.service;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import org.springframework.integration.annotation.Publisher;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import perobobbot.data.service.EventService;
import perobobbot.data.service.ExtensionService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.data.service.proxy.ProxyExtensionService;
import perobobbot.lang.GatewayChannels;

@Service
@EventService
public class EventExtensionService extends ProxyExtensionService {

    public EventExtensionService(@NonNull @UnsecuredService ExtensionService delegate) {
        super(delegate);
    }

    @Override
    @Publisher(GatewayChannels.EVENT_MESSAGES)
    @Payload("T(perobobbot.data.service.event.ExtensionEvent).create('UPDATE')")
    public void updateExtensionList(@NonNull ImmutableSet<String> foundExtensionNames) {
        super.updateExtensionList(foundExtensionNames);
    }

    @Override
    @Publisher(GatewayChannels.EVENT_MESSAGES)
    @Payload("#return?T(perobobbot.data.service.event.ExtensionEvent).create('ACTIVATE'):null")
    public boolean activateExtension(@NonNull String extensionName) {
        return super.activateExtension(extensionName);
    }

    @Override
    @Publisher(GatewayChannels.EVENT_MESSAGES)
    @Payload("#return?T(perobobbot.data.service.event.ExtensionEvent).create('DEACTIVATE'):null")
    public boolean deactivateExtension(@NonNull String extensionName) {
        return super.deactivateExtension(extensionName);
    }
}
