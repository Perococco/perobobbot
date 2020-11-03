package perobobbot.console.spring;

import lombok.NonNull;
import perobobbot.common.lang.Packages;

import java.util.stream.Stream;

public class ConsoleIOPackages implements Packages {

    @Override
    public @NonNull String context() {
        return "Console IO";
    }

    @Override
    public @NonNull Stream<String> stream() {
        return Stream.of(ConsoleIOPackages.class.getPackageName());
    }
}
