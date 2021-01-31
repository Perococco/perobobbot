package perobobbot.data.security.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import perobobbot.data.service.EventService;
import perobobbot.data.service.PointService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.proxy.ProxyPointService;
import perobobbot.lang.*;

import java.time.Duration;
import java.util.UUID;

@Service
@SecuredService
public class SecuredPointService extends ProxyPointService {

    public SecuredPointService(@NonNull @EventService PointService delegate) {
        super(delegate);
    }

    @Override
    public @NonNull Safe findSafe(@NonNull String userId, @NonNull Platform platform, @NonNull String channelName, @NonNull PointType pointType) {
        return super.findSafe(userId, platform, channelName, pointType);
    }

    @Override
    public @NonNull Balance getBalance(@NonNull UUID safeId) {
        return super.getBalance(safeId);
    }

    @Override
    public @NonNull Transaction createTransaction(@NonNull UUID safeId, long requestedAmount, @NonNull Duration duration) {
        return super.createTransaction(safeId, requestedAmount, duration);
    }

    @Override
    public @NonNull void cancelTransaction(@NonNull UUID transactionId) {
        super.cancelTransaction(transactionId);
    }

    @Override
    public @NonNull void performTransaction(@NonNull UUID transactionId) {
        super.performTransaction(transactionId);
    }
}
