package perobobbot.data.com;

import lombok.NonNull;

import java.util.UUID;

public class CancelledTransaction extends DataException {

    public CancelledTransaction(@NonNull UUID transactionId) {
        super("the transaction '"+transactionId+"' has been cancelled");
    }
}
