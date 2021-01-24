package perobobbot.action;

import lombok.NonNull;

public interface Action<P,R> {

    R execute(@NonNull P parameter) throws Throwable;

}
