package perobobbot.data.event.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import perobobbot.data.service.EventService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.data.service.ViewerIdentityService;
import perobobbot.data.service.proxy.ProxyViewerIdentityService;

@Service
@EventService
public class EventViewerIdentityService extends ProxyViewerIdentityService {

    public EventViewerIdentityService(@NonNull @UnsecuredService ViewerIdentityService delegate) {
        super(delegate);
    }
}
