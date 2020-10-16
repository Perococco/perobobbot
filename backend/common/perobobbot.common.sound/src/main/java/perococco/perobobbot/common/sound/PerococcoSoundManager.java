package perococco.perobobbot.common.sound;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.common.lang.MapTool;
import perobobbot.common.lang.ThrowableTool;
import perobobbot.common.lang.fp.Try2;
import perobobbot.common.sound.NDIAudioFormat;
import perobobbot.common.sound.Sound;
import perobobbot.common.sound.SoundManager;
import perobobbot.common.sound.SoundRegistrationFailure;

import javax.sound.sampled.AudioFormat;
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
    private final NDIAudioFormat targetFormat;

    @NonNull
    private final Try2<? super URL, @NonNull ? super NDIAudioFormat, ? extends SoundResource, Throwable> soundResourceFactory;

    public PerococcoSoundManager(float sampleRate, @NonNull Try2<? super URL, @NonNull ? super NDIAudioFormat, ? extends SoundResource, Throwable> soundResourceFactory) {
        this(new NDIAudioFormat(sampleRate),soundResourceFactory);
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
