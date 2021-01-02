package perobobbot.data.event.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import perobobbot.data.service.EventService;
import perobobbot.data.service.ExtensionService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.data.service.proxy.ProxyExtensionService;

@Service
@EventService
public class EventExtensionService extends ProxyExtensionService {

    public EventExtensionService(@NonNull @UnsecuredService ExtensionService delegate) {
        super(delegate);
    }

}
