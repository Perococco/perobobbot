package perobobbot.extension;

import lombok.NonNull;
import perobobbot.lang.Balance;
import perobobbot.lang.ChatUser;
import perobobbot.lang.Platform;
import perobobbot.lang.PointType;

public interface StoreController {

    @NonNull Balance getBalance(@NonNull ChatUser user, @NonNull String channelName, @NonNull PointType pointType);

    @NonNull Balance addToBalance(@NonNull String userId, @NonNull Platform platform, @NonNull String channelName, @NonNull PointType pointType, int amount);

}
