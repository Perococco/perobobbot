package perobobbot.play.spring;

import lombok.NonNull;
import perobobbot.lang.PerobobbotException;
import perobobbot.play.SoundResolver;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;

public class DebugSoundResolver implements SoundResolver {
    @Override
    public @NonNull Optional<URL> resolveSound(@NonNull String name) {
        try {
            return Optional.of(Path.of("/home/perococco/pas_faux.wav").toUri().toURL());
        } catch (MalformedURLException e) {
            throw new PerobobbotException("Fail to resolve sound URL for '"+name+"'",e);
        }
    }
}
