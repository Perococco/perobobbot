package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.TransactionState;

@Getter
public class InvalidTransactionState extends DataException {

    private final @NonNull TransactionState actual;

    public InvalidTransactionState(@NonNull TransactionState actual) {
        super("Transaction is in invalid state: actual='"+actual+"'");
        this.actual = actual;
    }
}
