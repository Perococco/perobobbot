package perobobbot.localio.spring;

import lombok.NonNull;
import perobobbot.lang.Packages;

import java.util.stream.Stream;

public class LocalIOPackages implements Packages {

    @Override
    public @NonNull String context() {
        return "[Extension] Local IO";
    }

    @Override
    public @NonNull Stream<String> stream() {
        return Stream.of(LocalIOPackages.class.getPackageName());
    }
}
