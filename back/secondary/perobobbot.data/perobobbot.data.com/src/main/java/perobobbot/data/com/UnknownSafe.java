package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

public class UnknownSafe extends DataException {

    @NonNull
    @Getter
    private final UUID safeId;

    public UnknownSafe(@NonNull UUID safeId) {
        super("Could not find any safe with id='"+safeId+"'");
        this.safeId = safeId;
    }
}
