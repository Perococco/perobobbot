package perobobbot.data.event.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import perobobbot.data.service.EventService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.data.service.PlatformUserService;
import perobobbot.data.service.proxy.ProxyPlatformUserService;

@Service
@EventService
public class EventPlatformUserService extends ProxyPlatformUserService {

    public EventPlatformUserService(@NonNull @UnsecuredService PlatformUserService delegate) {
        super(delegate);
    }
}
