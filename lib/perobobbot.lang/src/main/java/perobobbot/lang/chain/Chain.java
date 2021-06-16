package perobobbot.lang.chain;

import lombok.NonNull;

public interface Chain<P> {

    void callNext(@NonNull P parameter);
}
