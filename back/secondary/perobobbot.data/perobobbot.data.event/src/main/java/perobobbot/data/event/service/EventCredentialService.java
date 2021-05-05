package perobobbot.data.event.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import perobobbot.data.com.DataCredentialInfo;
import perobobbot.data.service.CredentialService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.data.service.proxy.ProxyCredentialService;
import perobobbot.lang.Platform;

@Service
@EventService
public class EventCredentialService extends ProxyCredentialService {

    public EventCredentialService(@NonNull @UnsecuredService CredentialService delegate) {
        super(delegate);
    }

    @Override
    public @NonNull DataCredentialInfo createCredential(@NonNull String login, @NonNull Platform platform) {
        return super.createCredential(login, platform);
    }
}
