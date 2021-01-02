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

}
