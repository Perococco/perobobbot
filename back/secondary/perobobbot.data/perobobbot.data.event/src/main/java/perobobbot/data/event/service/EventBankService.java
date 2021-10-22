package perobobbot.data.event.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import perobobbot.data.service.BankService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.data.service.proxy.ProxyBankService;
import perobobbot.lang.PluginService;

@Service
@EventService
@PluginService(type = BankService.class, apiVersion = BankService.VERSION,sensitive = true)
public class EventBankService extends ProxyBankService {

    public EventBankService(@NonNull @UnsecuredService BankService delegate) {
        super(delegate);
    }


}
