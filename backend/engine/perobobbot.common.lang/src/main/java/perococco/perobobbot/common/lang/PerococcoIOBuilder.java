package perococco.perobobbot.common.lang;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.common.lang.IO;
import perobobbot.common.lang.IOBuilder;
import perobobbot.common.lang.Platform;
import perobobbot.common.lang.PlatformIO;

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
