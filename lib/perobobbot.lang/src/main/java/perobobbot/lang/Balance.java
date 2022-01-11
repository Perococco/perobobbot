package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class Balance {

    /**
     * The id of the safe
     */
    @NonNull UUID safeId;
    /**
     * the type of the point in the safe
     */
    @NonNull PointType pointType;

    /**
     * how many point are in the safe
     */
    long credit;

}
