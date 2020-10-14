package perococco.perobobbot.common.sound;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.common.lang.MapTool;
import perobobbot.common.lang.fp.Function1;
import perobobbot.common.sound.Sound;
import perobobbot.common.sound.SoundManager;

import java.net.URL;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PerococcoSoundManager implements SoundManager {

    @NonNull
    public static PerococcoSoundManager provider() {
        return null;
    }

    private ImmutableMap<UUID, SoundResource> soundResources = ImmutableMap.of();

    @NonNull
    private final Function1<? super URL, ? extends SoundResource> soundResourceFactory;

    @Override
    @Synchronized
    public @NonNull UUID registerSound(@NonNull URL soundPath) {
        final SoundResource soundResource = soundResourceFactory.f(soundPath);
        final UUID uuid = UUID.randomUUID();
        this.soundResources = MapTool.add(soundResources,uuid,soundResource);
        return uuid;
    }


    @Override
    @Synchronized
    public void unregisterSound(@NonNull UUID uuid) {
        this.soundResources = MapTool.remove(soundResources,uuid);
    }

    @Override
    public @NonNull Optional<Sound> createSound(@NonNull UUID uuid) {
        return Optional.ofNullable(soundResources.get(uuid))
                       .map(SoundResource::createSound);
    }
}
