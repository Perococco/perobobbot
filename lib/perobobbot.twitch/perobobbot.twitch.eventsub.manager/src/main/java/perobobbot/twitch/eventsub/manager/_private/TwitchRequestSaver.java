package perobobbot.twitch.eventsub.manager._private;

import lombok.NonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TwitchRequestSaver {

    private final Path root;

    public TwitchRequestSaver() {
        var root = Path.of(System.getProperty("user.home")).resolve("twitch_notification");
        try {
            Files.createDirectories(root);
        } catch (Throwable t) {
            t.printStackTrace();
            root = null;
        }
        this.root = root;

    }

    public void saveBody(@NonNull byte[] content) {
        if (this.root == null) {
            return;
        }
        try {
            final var outputFile = Files.createTempFile(this.root,"notification_",".json");
            Files.write(outputFile, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
