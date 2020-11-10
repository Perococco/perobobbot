package perobobbot.localio.spring;

import lombok.NonNull;
import perobobbot.common.lang.Packages;
import perobobbot.localio.LocalIO;

import java.util.stream.Stream;

public class LocalIOPackages implements Packages {

    @Override
    public @NonNull String context() {
        return LocalIO.EXTENSION_NAME;
    }

    @Override
    public @NonNull Stream<String> stream() {
        return Stream.of(LocalIOPackages.class.getPackageName());
    }
}