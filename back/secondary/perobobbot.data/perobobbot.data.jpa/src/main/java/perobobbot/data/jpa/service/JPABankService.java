package perobobbot.data.jpa.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.domain.SafeEntity;
import perobobbot.data.jpa.repository.SafeRepository;
import perobobbot.data.jpa.repository.TransactionRepository;
import perobobbot.data.jpa.repository.ViewerIdentityRepository;
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
    private final @NonNull ViewerIdentityRepository viewerIdentityRepository;

    @Override
    @Transactional
    public @NonNull Safe findSafe(@NonNull UserOnChannel userOnChannel, @NonNull PointType pointType) {
        return getOrCreateSafe(userOnChannel, pointType).toView();
    }

    @Override
    @Transactional
    public void cleanTransactions() {
        final var now = instants.now();
        final var transactions = transactionRepository.findAllByStateEqualsAndExpirationTimeBefore(
                TransactionState.PENDING, now);
        transactions.forEach(t -> t.rollback().removeFromSafe());
        transactionRepository.deleteInBatch(transactions);
    }

    @Override
    public @NonNull Balance getBalance(@NonNull UUID safeId) {
        var safe = safeRepository.getByUuid(safeId);
        return new Balance(safe.toView(), safe.getCredit());
    }

    @Override
    public @NonNull Balance getBalance(@NonNull UserOnChannel userOnChannel, @NonNull PointType pointType) {
        return getOrCreateSafe(userOnChannel, pointType).toBalance();
    }

    @Override
    @Transactional
    public @NonNull TransactionInfo createTransaction(@NonNull UUID safeId, long requestedAmount, @NonNull Duration duration) {
        final var now = instants.now();
        final var safe = safeRepository.getByUuid(safeId);
        final var transaction = safe.createTransaction(requestedAmount, now.plus(duration));

        return transactionRepository.save(transaction).toView();
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

    @Override
    @Transactional
    public @NonNull Balance addPoints(@NonNull UUID safeId, int amount) {
        final var safe = safeRepository.getByUuid(safeId);
        safe.addToAmount(amount);
        safeRepository.save(safe);
        return new Balance(safe.toView(), safe.getCredit());
    }

    private @NonNull SafeEntity getOrCreateSafe(@NonNull UserOnChannel userOnChannel, @NonNull PointType pointType) {
        final var viewerIdentity = viewerIdentityRepository.getByPlatformAndViewerId(userOnChannel.getPlatform(),
                                                                                     userOnChannel.getUserId());

        final var safe = safeRepository.findByChannelNameAndViewerIdentity_Uuid(userOnChannel.getChannelName(),
                                                                                viewerIdentity.getUuid())
                                       .orElse(null);


        if (safe == null) {
            return safeRepository.save(viewerIdentity.createSafe(userOnChannel.getChannelName()));
        }
        return safe;
    }
}
