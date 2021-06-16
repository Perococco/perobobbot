package perobobbot.lang.chain;

import lombok.NonNull;

public interface Link<P> {

    void call(@NonNull P parameter, @NonNull Chain<P> chain);
}
