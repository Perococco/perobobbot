package perobobbot.data.jpa.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.domain.SafeEntity;
import perobobbot.data.jpa.repository.SafeRepository;
import perobobbot.data.jpa.repository.TransactionRepository;
import perobobbot.data.service.PointService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.lang.*;

import java.time.Duration;
import java.util.UUID;

@Service
@UnsecuredService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JPAPointService implements PointService {

    private final @NonNull InstantProvider instantProvider;

    private final @NonNull SafeRepository safeRepository;
    private final @NonNull TransactionRepository transactionRepository;

    @Override
    @Transactional
    public @NonNull Safe findSafe(@NonNull String userId, @NonNull Platform platform, @NonNull String channelName, @NonNull PointType pointType) {
        var safe = safeRepository.findByPlatformAndChannelNameAndUserChatIdAndType(platform,channelName,userId,pointType)
                .orElse(null);
        if (safe == null) {
            safe = safeRepository.save(new SafeEntity(platform,channelName,userId,pointType));
        }

        return safe.toView();
    }

    @Override
    public @NonNull Balance getBalance(@NonNull UUID safeId) {
        var safe = safeRepository.getByUuid(safeId);
        return new Balance(safe.toView(),safe.getAmount());
    }

    @Override
    @Transactional
    public @NonNull Transaction createTransaction(@NonNull UUID safeId, long requestedAmount, @NonNull Duration duration) {
        final var now = instantProvider.getNow();
        final var safe = safeRepository.getByUuid(safeId);
        final var transaction = safe.startTransaction(requestedAmount,now.plus(duration));

        safeRepository.save(safe);

        return transaction.toView();
    }

    @Override
    @Transactional
    public @NonNull void cancelTransaction(@NonNull UUID transactionId) {
        final var transaction = transactionRepository.getByUuid(transactionId);
        transaction.cancel();
        transaction.removeFromSafe();

        transactionRepository.save(transaction);

    }

    @Override
    @Transactional
    public @NonNull void performTransaction(@NonNull UUID transactionId) {
        final var transaction = transactionRepository.getByUuid(transactionId);
        transaction.perform(instantProvider.getNow());
        transaction.removeFromSafe();

        transactionRepository.save(transaction);

    }

    @Override
    @Transactional
    public @NonNull Balance addPoints(@NonNull UUID safeId, int amount) {
        final var safe = safeRepository.getByUuid(safeId);
        safe.addToAmount(Math.abs(amount));
        safeRepository.save(safe);
        return new Balance(safe.toView(),safe.getAmount());
    }
}
