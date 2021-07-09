package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class Balance {

    @NonNull UUID safeId;
    @NonNull PointType pointType;
    @NonNull long credit;

}
