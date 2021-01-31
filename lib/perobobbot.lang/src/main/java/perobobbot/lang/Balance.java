package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;

@Value
public class Balance {

    @NonNull Safe safe;

    long amount;
}
