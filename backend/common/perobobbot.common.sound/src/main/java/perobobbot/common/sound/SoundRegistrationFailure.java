package perobobbot.common.sound;

import lombok.NonNull;
import perobobbot.common.lang.PerobobbotException;

import java.net.URL;

public class SoundRegistrationFailure extends PerobobbotException {

    @NonNull
    private final URL soundResourceUrl;

    public SoundRegistrationFailure(@NonNull URL soundResourceUrl, @NonNull Throwable cause) {
        super("Could not register sound '"+soundResourceUrl+"'",cause);
        this.soundResourceUrl = soundResourceUrl;
    }

}
