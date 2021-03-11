package perobobbot.server.plugin;

import com.google.common.collect.ImmutableSet;
import jplugman.tools.FolderListener;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;

@RequiredArgsConstructor
public class FolderListenerLogger implements FolderListener {

    private final @NonNull FolderListener delegate;


    @Override
    public void onFileCreated(@NonNull Path path) {
        System.out.println("FolderListenerLogger.onFileCreated("+path+")");;
        delegate.onFileCreated(path);
    }

    @Override
    public void onFileModified(@NonNull Path path) {
        System.out.println("FolderListenerLogger.onFileModified("+path+")");;
        delegate.onFileModified(path);
    }

    @Override
    public void onFileDeleted(@NonNull Path path) {
        System.out.println("FolderListenerLogger.onFileDeleted("+path+")");
        delegate.onFileDeleted(path);
    }

    @Override
    public void onStart(ImmutableSet<Path> paths) {
        System.out.println("FolderListenerLogger.onStart");
        delegate.onStart(paths);
    }
}
