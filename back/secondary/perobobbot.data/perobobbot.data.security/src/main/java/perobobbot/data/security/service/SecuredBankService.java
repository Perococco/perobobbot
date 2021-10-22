package perobobbot.data.security.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import perobobbot.data.service.BankService;
import perobobbot.data.service.EventService;
import perobobbot.lang.PointType;
import perobobbot.data.service.SecuredService;
import perobobbot.lang.*;

import java.time.Duration;
import java.util.UUID;

@Service
@SecuredService
@RequiredArgsConstructor
public class SecuredBankService implements BankService {

    private final @NonNull @EventService BankService delegate;


    @Override
    @PreAuthorize("isAuthenticated()")
    public @NonNull Safe findSafe(@NonNull Platform platform, @NonNull String viewerId, @NonNull String channelName) {
        return delegate.findSafe(platform, viewerId, channelName);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public @NonNull Safe findSafe(@NonNull UUID viewerIdentityId, @NonNull String channelName) {
        return delegate.findSafe(viewerIdentityId, channelName);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public @NonNull Safe getSafe(@NonNull UUID uuid) {
        return delegate.getSafe(uuid);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public @NonNull Balance addPoints(@NonNull UUID safeId, @NonNull PointType pointType, int amount) {
        return delegate.addPoints(safeId, pointType, amount);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public @NonNull TransactionInfo createTransaction(@NonNull UUID safeId, @NonNull PointType pointType, long requestedAmount, @NonNull Duration duration) {
        return delegate.createTransaction(safeId, pointType, requestedAmount, duration);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public void cancelTransaction(@NonNull UUID transactionId) {
        delegate.cancelTransaction(transactionId);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public void completeTransaction(@NonNull UUID transactionId) {
        delegate.completeTransaction(transactionId);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public void cleanTransactions() {
        delegate.cleanTransactions();
    }
}
