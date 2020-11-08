package perobobbot.consoleio.spring;

import lombok.NonNull;
import perobobbot.common.lang.Packages;

import java.util.stream.Stream;

public class ConsoleIOPackages implements Packages {

    @Override
    public @NonNull String context() {
        return "console-io";
    }

    @Override
    public @NonNull Stream<String> stream() {
        return Stream.of(ConsoleIOPackages.class.getPackageName());
    }
}
