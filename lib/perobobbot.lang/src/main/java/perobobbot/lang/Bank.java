package perobobbot.lang;

import lombok.NonNull;

public interface Bank {

    int VERSION = 1;

    @NonNull Balance getBalance(@NonNull UserOnChannel userOnChannel, @NonNull PointType pointType);

    @NonNull Balance addToBalance(@NonNull UserOnChannel userOnChannel, @NonNull PointType pointType, int amount);



}
