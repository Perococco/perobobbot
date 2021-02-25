package perobobbot.server.component;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import perobobbot.data.service.BankService;
import perobobbot.data.service.SecuredService;
import perobobbot.lang.*;
import perobobbot.plugin.PluginService;

@Component
@PluginService
@RequiredArgsConstructor
public class SpringBank implements Bank {

    @SecuredService
    private final @NonNull BankService bankService;

    @Override
    public @NonNull Balance getBalance(@NonNull UserOnChannel userOnChannel, @NonNull PointType pointType) {
        return bankService.getBalance(userOnChannel,pointType);
    }

    @Override
    public @NonNull Balance addToBalance(@NonNull UserOnChannel userOnChannel, @NonNull PointType pointType, int amount) {
        final Safe safe = bankService.findSafe(userOnChannel, pointType);
        return bankService.addPoints(safe.getId(), amount);
    }
}
