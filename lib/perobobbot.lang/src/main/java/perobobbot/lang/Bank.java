package perobobbot.lang;

import lombok.NonNull;

public interface Bank {

    @NonNull Balance getBalance(@NonNull UserOnChannel userOnChannel, @NonNull PointType pointType);

    @NonNull Balance addToBalance(@NonNull UserOnChannel userOnChannel, @NonNull PointType pointType, int amount);



}
