package perobobbot.data.event.service;

import lombok.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import perobobbot.data.service.*;
import perobobbot.data.service.proxy.ProxyExtensionService;
import perobobbot.data.service.proxy.ProxyUserService;

@Service
@EventService
public class EventUserService extends ProxyUserService {

    public EventUserService(@NonNull @UnsecuredService UserService delegate) {
        super(delegate);
    }


}
