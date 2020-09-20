package perococco.perobobbot.program.core;

import lombok.NonNull;
import perobobbot.common.lang.fp.Consumer0;

public interface NamedExecution extends Consumer0 {

    @NonNull
    String getName();

    void launch();

    @Override
    default void f() {
        launch();
    }
}
