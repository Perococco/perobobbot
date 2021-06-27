package perobobbot.lang.chain;

import lombok.NonNull;

public interface Chain<P,R> {

    @NonNull R callNext(@NonNull P parameter);
}
