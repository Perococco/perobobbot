package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Consumer1;

public interface StandardInputProvider {

    int VERSION = 1;

    @NonNull Subscription addListener(@NonNull Consumer1<? super String> listener);

}
