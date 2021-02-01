package perobobbot.server.component;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import perobobbot.data.service.BankService;
import perobobbot.data.service.SecuredService;
import perobobbot.extension.StoreController;
import perobobbot.lang.*;

@Component
@RequiredArgsConstructor
public class SpringStoreController implements StoreController {

    @SecuredService
    private final @NonNull BankService bankService;

    @Override
    public @NonNull Balance getBalance(@NonNull ChatUser user, @NonNull String channelName, @NonNull PointType pointType) {
        final Safe safe = bankService.findSafe(user.getUserId(), user.getPlatform(), channelName, pointType);
        return bankService.getBalance(safe.getId());
    }

    @Override
    public @NonNull Balance addToBalance(@NonNull String userId, @NonNull Platform platform, @NonNull String channelName, @NonNull PointType pointType, int amount) {
        final Safe safe = bankService.findSafe(userId, platform, channelName, pointType);
        return bankService.addPoints(safe.getId(), amount);
    }
}
