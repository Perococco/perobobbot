package perobobbot.data.event.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SubscriptionService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.data.service.proxy.ProxySubscriptionService;
import perobobbot.lang.PluginService;

@Service
@EventService
@PluginService(type = SubscriptionService.class, apiVersion = SubscriptionService.VERSION,sensitive = true)
public class EventSubscriptionService extends ProxySubscriptionService {

    public EventSubscriptionService(@NonNull @UnsecuredService SubscriptionService delegate) {
        super(delegate);
    }
}
