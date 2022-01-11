package perobobbot.data.jpa.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.jpa.repository.PlatformUserRepository;
import perobobbot.data.jpa.repository.SafeRepository;
import perobobbot.data.jpa.repository.TransactionRepository;
import perobobbot.data.service.BankService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.lang.*;

import java.time.Duration;
import java.util.UUID;

@Service
@UnsecuredService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JPABankService implements BankService {

    private final @NonNull Instants instants;

    private final @NonNull SafeRepository safeRepository;
    private final @NonNull TransactionRepository transactionRepository;
    private final @NonNull PlatformUserRepository platformUserRepository;

    @Override
    @Transactional
    public @NonNull Safe findSafe(@NonNull UUID platformUserId, @NonNull String channelId) {
        final var existing = safeRepository.findByChannelIdAndPlatformUser_Uuid(channelId,platformUserId)
                .orElse(null);
        if (existing != null) {
            return existing.toView();
        }
        final var platformUser = platformUserRepository.getByUuid(platformUserId);
        final var safe = platformUser.createSafe(channelId);
        return safeRepository.save(safe).toView();
    }

    @Override
    @Transactional
    public @NonNull Safe findSafe(@NonNull Platform platform, @NonNull String userId, @NonNull String channelId) {
        //IMPROVE might want a method in the repository to get the UUID only instead of the whole entity
        final var platformUser = platformUserRepository.getByPlatformAndUserId(platform, userId);
        return findSafe(platformUser.getUuid(),channelId);
    }

    @Override
    public @NonNull Safe getSafe(@NonNull UUID uuid) {
        return safeRepository.getByUuid(uuid).toView();
    }

    @Override
    @Transactional
    public @NonNull Balance addPoints(@NonNull UUID safeId, @NonNull PointType pointType, long amount) {
        final var safe = safeRepository.getByUuid(safeId);
        safe.addToAmount(pointType,amount);
        return safeRepository.save(safe).toBalance(pointType);
    }

    @Override
    @Transactional
    public @NonNull TransactionInfo createTransaction(@NonNull UUID safeId,
                                                      @NonNull PointType pointType,
                                                      long requestedAmount,
                                                      @NonNull Duration duration) {
        final var now = instants.now();
        final var safe = safeRepository.getByUuid(safeId);
        final var transaction = safe.createTransaction(pointType, requestedAmount, now.plus(duration));

        return transactionRepository.save(transaction).toView();
    }

    @Override
    @Transactional
    @Scheduled(fixedDelay = 10_000)
    public void cleanTransactions() {
        final var now = instants.now();
        final var transactions = transactionRepository.findAllByStateEqualsAndExpirationTimeBefore(
                TransactionState.PENDING, now);
        transactions.forEach(t -> t.rollback().removeFromSafe());
        transactionRepository.deleteAllInBatch(transactions);
    }


    @Override
    @Transactional
    public void cancelTransaction(@NonNull UUID transactionId) {
        final var transaction = transactionRepository.getByUuid(transactionId);
        transactionRepository.delete(transaction.rollback().removeFromSafe());
    }

    @Override
    @Transactional
    public void completeTransaction(@NonNull UUID transactionId) {
        final var now = instants.now();
        final var transaction = transactionRepository.getByUuid(transactionId);
        transactionRepository.delete(transaction.complete(now).removeFromSafe());
    }

}
