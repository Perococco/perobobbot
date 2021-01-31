package perobobbot.data.com;

import lombok.NonNull;

import java.util.UUID;

public class ExpiredTransaction extends DataException {

    public ExpiredTransaction(@NonNull UUID transactionId) {
        super("the transaction '"+transactionId+"' has expired");
    }
}
