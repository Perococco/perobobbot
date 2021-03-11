package perobobbot.chat.core;

import lombok.NonNull;
import perobobbot.lang.Subscription;
import perococco.perobobbot.chat.core.WithMapIO;

import java.util.Optional;

public interface MutableIO extends DisposableIO {

    static @NonNull MutableIO create() {
        return new WithMapIO();
    }

    @NonNull Optional<Subscription> addPlatform(@NonNull ChatPlatform chatPlatform);

}
