package perobobbot.access;

import lombok.NonNull;
import perobobbot.lang.fp.Consumer1;

import java.util.UUID;

public interface AccessPoint<P> extends Consumer1<P> {

    @NonNull
    UUID getId();

    void execute(@NonNull P parameter);

    @Override
    default void f(@NonNull P p) {
        execute(p);
    }
}
