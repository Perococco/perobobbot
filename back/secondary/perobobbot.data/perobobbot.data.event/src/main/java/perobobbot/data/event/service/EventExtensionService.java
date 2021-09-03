package perobobbot.data.event.service;

import lombok.NonNull;
import org.springframework.integration.annotation.Publisher;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import perobobbot.data.com.Extension;
import perobobbot.data.com.UpdateExtensionParameters;
import perobobbot.data.service.EventService;
import perobobbot.data.service.ExtensionService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.data.service.proxy.ProxyExtensionService;
import perobobbot.lang.GatewayChannels;

import java.util.UUID;

@Service
@EventService
public class EventExtensionService extends ProxyExtensionService {

    public EventExtensionService(@NonNull @UnsecuredService ExtensionService delegate) {
        super(delegate);
    }

    @Override
    @Publisher(GatewayChannels.EVENT_MESSAGES)
    @Payload("T(perobobbot.data.com.event.ExtensionEvent).create('AVAILABLE')")
    public void setExtensionAvailable(@NonNull String extensionName) {
        super.setExtensionAvailable(extensionName);
    }

    @Override
    @Publisher(GatewayChannels.EVENT_MESSAGES)
    @Payload("T(perobobbot.data.com.event.ExtensionEvent).create('UNAVAILABLE')")
    public void setExtensionUnavailable(@NonNull String extensionName) {
        super.setExtensionUnavailable(extensionName);
    }

    @Override
    @Publisher(GatewayChannels.EVENT_MESSAGES)
    @Payload("#return?T(perobobbot.data.com.event.ExtensionEvent).create('ACTIVATED'):null")
    public boolean activateExtension(@NonNull String extensionName) {
        return super.activateExtension(extensionName);
    }

    @Override
    @Publisher(GatewayChannels.EVENT_MESSAGES)
    @Payload("#return?T(perobobbot.data.com.event.ExtensionEvent).create('DEACTIVATED'):null")
    public boolean deactivateExtension(@NonNull String extensionName) {
        return super.deactivateExtension(extensionName);
    }

    @Override
    @Publisher(GatewayChannels.EVENT_MESSAGES)
    @Payload("T(perobobbot.data.com.event.ExtensionEvent).create('UPDATED')")
    public @NonNull Extension updateExtension(@NonNull UUID extensionId, @NonNull UpdateExtensionParameters updateExtensionParameters) {
        return super.updateExtension(extensionId, updateExtensionParameters);
    }
}
