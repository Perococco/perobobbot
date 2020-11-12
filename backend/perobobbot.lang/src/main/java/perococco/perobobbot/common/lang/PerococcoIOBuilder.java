package perococco.perobobbot.common.lang;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.lang.IO;
import perobobbot.lang.IOBuilder;
import perobobbot.lang.Platform;
import perobobbot.lang.PlatformIO;

import java.util.HashMap;
import java.util.Map;

public class PerococcoIOBuilder implements IOBuilder {

    private final Map<Platform, PlatformIO> platforms = new HashMap<>();

    @Override
    public IO build() {
        return new WithMapIO(ImmutableMap.copyOf(platforms));
    }

    @Override
    public @NonNull IOBuilder add(@NonNull Platform platform, @NonNull PlatformIO platformIO) {
        platforms.put(platform,platformIO);
        return this;
    }
}
