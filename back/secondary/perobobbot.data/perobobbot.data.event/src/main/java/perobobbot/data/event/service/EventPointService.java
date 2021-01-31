package perobobbot.data.event.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import perobobbot.data.service.BotService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.PointService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.data.service.proxy.ProxyBotService;
import perobobbot.data.service.proxy.ProxyPointService;

@Service
@EventService
public class EventPointService extends ProxyPointService {

    public EventPointService(@NonNull @UnsecuredService PointService delegate) {
        super(delegate);
    }


}
