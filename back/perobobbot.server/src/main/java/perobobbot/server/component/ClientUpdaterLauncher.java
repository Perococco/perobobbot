package perobobbot.server.component;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@Log4j2
public class ClientUpdaterLauncher {

    private final @NonNull ClientUpdater clientUpdater;
    private final @NonNull Path triggerPath;

    public ClientUpdaterLauncher(@NonNull ClientUpdater clientUpdater) {
        this.clientUpdater = clientUpdater;
        final var directory = clientUpdater.getClientInfoPath().getParent();
        final var triggerFileName = clientUpdater.getClientInfoPath().getFileName().toFile()+".lock";
        this.triggerPath = directory.resolve(triggerFileName);
    }

    @Scheduled(fixedRate = 10_000 )
    public void checkForUpdate() throws IOException {
        if (Files.deleteIfExists(triggerPath)) {
            LOG.info("Update client information from '{}'",clientUpdater.getClientInfoPath());
            clientUpdater.update();
        }
    }

}
