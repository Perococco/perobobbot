package perobobbot.server.component;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import perobobbot.data.service.PointService;
import perobobbot.data.service.SecuredService;
import perobobbot.extension.StoreController;
import perobobbot.lang.*;

@Component
@RequiredArgsConstructor
public class SpringStoreController implements StoreController {

    @SecuredService
    private final @NonNull PointService pointService;

    @Override
    public @NonNull Balance getBalance(@NonNull ChatUser user, @NonNull String channelName, @NonNull PointType pointType) {
        final Safe safe = pointService.findSafe(user.getUserId(), user.getPlatform(), channelName, pointType);
        return pointService.getBalance(safe.getId());
    }

    @Override
    public @NonNull Balance addToBalance(@NonNull String userId, @NonNull Platform platform, @NonNull String channelName, @NonNull PointType pointType, int amount) {
        final Safe safe = pointService.findSafe(userId, platform, channelName, pointType);
        return pointService.addPoints(safe.getId(),amount);
    }
}
