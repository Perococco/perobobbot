package perococco.perobobbot.program.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import perobobbot.common.lang.fp.Consumer0;

@Value
public class NamedExecution implements Consumer0 {

    @Getter
    @NonNull String name;

    @NonNull Runnable action;

    public void launch() {
        action.run();
    }

    @Override
    public void f() {
        this.launch();
    }
}
