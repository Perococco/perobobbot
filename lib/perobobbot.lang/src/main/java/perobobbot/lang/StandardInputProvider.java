package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Consumer1;

public interface StandardInputProvider {

    @NonNull Subscription addListener(@NonNull Consumer1<? super String> listener);

}
