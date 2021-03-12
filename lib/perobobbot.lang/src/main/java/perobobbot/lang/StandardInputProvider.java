package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Consumer1;

public interface StandardInputProvider {

    String VERSION = "1.0.0";

    @NonNull Subscription addListener(@NonNull Consumer1<? super String> listener);

}
