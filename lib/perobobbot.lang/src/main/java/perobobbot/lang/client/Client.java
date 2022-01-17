package perobobbot.lang.client;

import lombok.NonNull;
import perobobbot.lang.Platform;

import java.util.UUID;

public interface Client<T> {

    @NonNull UUID getId();
    @NonNull Platform getPlatform();
    @NonNull String getClientId();
    @NonNull T getClientSecret();


    @NonNull SafeClient stripSecret();

}
