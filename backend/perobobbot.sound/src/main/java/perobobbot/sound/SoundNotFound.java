package perobobbot.sound;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.PerobobbotException;

public class SoundNotFound extends PerobobbotException {

    @Getter
    private final @NonNull String name;

    public SoundNotFound(@NonNull String name) {
        super("Could not find sound with name '"+name+"'");
        this.name = name;
    }
}
