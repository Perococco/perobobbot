package perobobbot.server.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.plugin.PluginService;
import perobobbot.sound.SoundResolver;

@RequiredArgsConstructor
public class SpringSoundResolverWrapper implements SoundResolver, PluginService {

    @Delegate
    private final @NonNull SoundResolver soundResolver;
}
