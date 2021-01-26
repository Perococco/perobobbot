package perobobbot.poll;

import lombok.NonNull;
import lombok.Value;

@Value
public class Vote {

    @NonNull Voter voter;
    @NonNull String choice;
}
