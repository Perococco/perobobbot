package perobobbot.sound;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.PerobobbotException;

import java.net.URL;

public class SoundRegistrationFailure extends PerobobbotException {

    @NonNull
    @Getter
    private final URL soundResourceUrl;

    public SoundRegistrationFailure(@NonNull URL soundResourceUrl, @NonNull Throwable cause) {
        super("Could not register sound '"+soundResourceUrl+"'",cause);
        this.soundResourceUrl = soundResourceUrl;
    }

}
