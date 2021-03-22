package perobobbot.server.plugin;

import jplugman.tools.FolderWatcher;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

@RequiredArgsConstructor
public class DelayedFolderWatcher implements FolderWatcher, ApplicationListener<ContextRefreshedEvent> {

    @Delegate
    private final @NonNull FolderWatcher folderWatcher;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        folderWatcher.start();
    }
}
