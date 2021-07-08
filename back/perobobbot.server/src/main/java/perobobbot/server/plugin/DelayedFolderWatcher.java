package perobobbot.server.plugin;

import jplugman.tools.FolderWatcher;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

@RequiredArgsConstructor
@Log4j2
public class DelayedFolderWatcher implements FolderWatcher, ApplicationListener<ApplicationStartedEvent> {

    @Delegate
    private final @NonNull FolderWatcher folderWatcher;

    @Override
    public void onApplicationEvent(@NonNull ApplicationStartedEvent event) {
        LOG.info("Start watching plugin folder.");
        folderWatcher.start();
    }
}
