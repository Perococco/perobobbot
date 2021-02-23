package perobobbot.data.event.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import perobobbot.data.service.BotService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.data.service.proxy.ProxyBotService;

import java.util.UUID;

@Service
@EventService
public class EventBotService extends ProxyBotService {

    public EventBotService(@NonNull @UnsecuredService BotService delegate) {
        super(delegate);
    }


}
