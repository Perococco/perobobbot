package perococco.perobobbot.sound;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.lang.MapTool;
import perobobbot.lang.ThrowableTool;
import perobobbot.lang.fp.Try2;
import perobobbot.sound.NDIAudioFormat;
import perobobbot.sound.Sound;
import perobobbot.sound.SoundManager;
import perobobbot.sound.SoundRegistrationFailure;

import java.net.URL;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PerococcoSoundManager implements SoundManager {

    @NonNull
    public static SoundManager create(int sampleRate) {
        return new PerococcoSoundManager(sampleRate,InMemorySoundResource::create);
    }

    private ImmutableMap<UUID, SoundResource> soundResources = ImmutableMap.of();

    @NonNull
    private final NDIAudioFormat targetFormat;

    @Getter
    private final int sampleRate;

    @Override
    public int getNbChannels() {
        return 2;
    }

    @NonNull
    private final Try2<? super URL, @NonNull ? super NDIAudioFormat, ? extends SoundResource, Throwable> soundResourceFactory;

    public PerococcoSoundManager(int sampleRate, @NonNull Try2<? super URL, @NonNull ? super NDIAudioFormat, ? extends SoundResource, Throwable> soundResourceFactory) {
        this(new NDIAudioFormat(sampleRate),sampleRate,soundResourceFactory);
    }

    @Override
    @Synchronized
    public @NonNull UUID registerSoundResource(@NonNull URL soundPath) {
        try {
            final SoundResource soundResource = soundResourceFactory.f(soundPath, targetFormat);
            final UUID uuid = UUID.randomUUID();
            this.soundResources = MapTool.add(soundResources, uuid, soundResource);
            return uuid;
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            throw new SoundRegistrationFailure(soundPath,t);
        }
    }


    @Override
    @Synchronized
    public void unregisterSoundResource(@NonNull UUID uuid) {
        this.soundResources = MapTool.remove(soundResources,uuid);
    }

    @Override
    public @NonNull Optional<Sound> createSound(@NonNull UUID uuid) {
        return Optional.ofNullable(soundResources.get(uuid))
                       .map(SoundResource::createSound);
    }
}