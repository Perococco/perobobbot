package perobobbot.data.service.proxy;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.data.service.BankService;


@RequiredArgsConstructor
public class ProxyBankService implements BankService {

    @Delegate
    private final @NonNull BankService delegate;
}
