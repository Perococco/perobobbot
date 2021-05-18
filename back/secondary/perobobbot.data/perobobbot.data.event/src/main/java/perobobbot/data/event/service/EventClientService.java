package perobobbot.data.event.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.data.service.proxy.ProxyClientService;

@Service
@EventService
public class EventClientService extends ProxyClientService {

    public EventClientService(@NonNull @UnsecuredService ClientService clientService) {
        super(clientService);
    }
}
