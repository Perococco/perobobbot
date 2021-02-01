package perobobbot.data.security.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import perobobbot.data.service.BankService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.proxy.ProxyBankService;

@Service
@SecuredService
public class SecuredBankService extends ProxyBankService {

    public SecuredBankService(@NonNull @EventService BankService delegate) {
        super(delegate);
    }

}
