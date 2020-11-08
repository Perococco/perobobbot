package perobobbot.overlay.newtek;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.sound.Sound;
import perobobbot.common.sound.SoundManager;
import perobobbot.overlay.api.SoundContext;
import perobobbot.overlay.api.SoundExecution;

import java.util.*;

@RequiredArgsConstructor
public class NDISoundContext implements SoundContext {

    @NonNull
    private final SoundManager soundManager;

    @NonNull
    private final List<Sound> sounds = new LinkedList<>();

    @Override
    public @NonNull SoundExecution playSound(@NonNull UUID soundId) {
        final Sound sound = soundManager.createSound(soundId).orElse(null);
        if (sound == null) {
            return SoundExecution.NOP;
        }
        sounds.add(sound);
        return sound::close;
    }

    public void fillAudioBuffer(@NonNull float[][] data, int nbSamples) {
        final Iterator<Sound> itr = sounds.iterator();
        boolean filled = false;
        while (itr.hasNext()) {
            final Sound sound = itr.next();
            if (sound.isCompleted()) {
                itr.remove();
                continue;
            }
            if (filled) {
                sound.addTo(data,nbSamples);
            } else {
                sound.copyTo(data, nbSamples);
                filled = true;
            }
        }
        if (!filled) {
            for (float[] datum : data) {
                fillWithZero(datum);
            }
        }
    }

    private void fillWithZero(@NonNull float[] data) {
        Arrays.fill(data, 0f);
    }

    public void releaseSounds() {
        sounds.forEach(Sound::close);
        sounds.clear();
    }
}
