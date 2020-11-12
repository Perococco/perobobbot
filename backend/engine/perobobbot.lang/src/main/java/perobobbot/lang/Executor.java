package perobobbot.lang;

import lombok.NonNull;

public interface Executor<P> {

    void execute(@NonNull P context);
}
