package perobobbot.pause;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.extension.ExtensionBase;
import perobobbot.lang.Todo;

import java.time.Duration;

public class PauseExtension extends ExtensionBase {

    @Getter
    private final @NonNull String userId;

    public PauseExtension(@NonNull String userId) {
        super(PauseExtensionFactory.NAME);
        this.userId = userId;
    }

    @Override
    public boolean isAutoStart() {
        return false;
    }

    public void startPause(@NonNull Duration duration) {
        System.out.println("SHOULD START PAUSE");
    }

    public void stopPause() {
        System.out.println("SHOULD STOP PAUSE");
    }
}
