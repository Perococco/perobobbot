package perobobbot.lang.chain;

import lombok.NonNull;

public interface Link<P,R> {

    @NonNull R call(@NonNull P parameter, @NonNull Chain<P,R> chain);
}
