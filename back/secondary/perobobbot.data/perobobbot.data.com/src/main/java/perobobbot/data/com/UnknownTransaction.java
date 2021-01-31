package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

public class UnknownTransaction extends DataException {

    @NonNull
    @Getter
    private final UUID transactionId;

    public UnknownTransaction(@NonNull UUID transactionId) {
        super("Could not find any safe with id='"+ transactionId +"'");
        this.transactionId = transactionId;
    }
}
