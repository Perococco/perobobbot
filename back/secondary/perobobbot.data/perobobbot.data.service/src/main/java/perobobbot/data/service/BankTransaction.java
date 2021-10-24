package perobobbot.data.service;

import lombok.NonNull;
import perobobbot.lang.PointType;
import perobobbot.lang.Safe;
import perobobbot.lang.Transaction;
import perobobbot.lang.TransactionInfo;

import java.time.Duration;
import java.util.UUID;

public class BankTransaction implements Transaction {

    private final BankService bankService;
    private final TransactionInfo transactionInfo;

    public BankTransaction(@NonNull BankService bankService, @NonNull UUID safeId, @NonNull PointType pointType, long requestedAmount, @NonNull Duration duration) {
        this.bankService = bankService;
        this.transactionInfo = bankService.createTransaction(safeId,pointType,requestedAmount,duration);
    }

    @Override
    public long amount() {
        return transactionInfo.getRequestedAmount();
    }

    @Override
    public void complete() {
        bankService.completeTransaction(transactionInfo.getId());
    }

    @Override
    public void rollback() {
        bankService.cancelTransaction(transactionInfo.getId());
    }
}
