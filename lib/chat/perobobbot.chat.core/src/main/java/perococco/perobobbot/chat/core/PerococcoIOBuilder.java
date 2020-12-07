package perococco.perobobbot.chat.core;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.chat.core.IO;
import perobobbot.chat.core.IOBuilder;
import perobobbot.lang.Platform;
import perobobbot.chat.core.ChatPlatform;

import java.util.HashMap;
import java.util.Map;

public class PerococcoIOBuilder implements IOBuilder {

    private final Map<Platform, ChatPlatform> platforms = new HashMap<>();

    @Override
    public IO build() {
        return new WithMapIO(ImmutableMap.copyOf(platforms));
    }

    @Override
    public @NonNull IOBuilder add(@NonNull Platform platform, @NonNull ChatPlatform chatPlatform) {
        platforms.put(platform, chatPlatform);
        return this;
    }
}
