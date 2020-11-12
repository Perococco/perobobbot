package perobobbot.greeter.spring;

import lombok.NonNull;
import perobobbot.lang.Packages;

import java.util.stream.Stream;

public class GreeterPackages implements Packages {

    @Override
    public @NonNull String context() {
        return "greeter";
    }

    @Override
    public @NonNull Stream<String> stream() {
        return Stream.of(GreeterPackages.class.getPackageName());
    }
}
