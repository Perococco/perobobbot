package perobobbot.lang;

import lombok.NonNull;

public interface Bank {

    String VERSION = "1.0.0";

    @NonNull Balance getBalance(@NonNull UserOnChannel userOnChannel, @NonNull PointType pointType);

    @NonNull Balance addToBalance(@NonNull UserOnChannel userOnChannel, @NonNull PointType pointType, int amount);



}
