package perobobbot.data.event.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import perobobbot.data.service.CredentialService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.data.service.UserService;
import perobobbot.data.service.proxy.ProxyCredentialService;
import perobobbot.data.service.proxy.ProxyUserService;

@Service
@EventService
public class EventCredentialService extends ProxyCredentialService {

    public EventCredentialService(@NonNull @UnsecuredService CredentialService delegate) {
        super(delegate);
    }


}
