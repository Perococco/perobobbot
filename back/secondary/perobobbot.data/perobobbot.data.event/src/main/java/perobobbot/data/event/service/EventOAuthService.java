package perobobbot.data.event.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import perobobbot.data.service.EventService;
import perobobbot.data.service.OAuthService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.data.service.proxy.ProxyOAuthService;

@Service
@EventService
public class EventOAuthService extends ProxyOAuthService {

    public EventOAuthService(@NonNull @UnsecuredService OAuthService delegate) {
        super(delegate);
    }

}
