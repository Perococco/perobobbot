package perobobbot.data.event.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import perobobbot.data.com.DataCredentialInfo;
import perobobbot.data.service.TokenService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.data.service.proxy.ProxyTokenService;
import perobobbot.lang.Platform;

@Service
@EventService
public class EventTokenService extends ProxyTokenService {

    public EventTokenService(@NonNull @UnsecuredService TokenService delegate) {
        super(delegate);
    }

}
