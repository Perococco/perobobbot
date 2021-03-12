package perobobbot.server.plugin;

import com.google.common.collect.ImmutableSet;
import jplugman.tools.FolderListener;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class FolderListenerLogger implements FolderListener {

    private final @NonNull FolderListener delegate;


    private <T> void log(@NonNull String message, @NonNull Consumer<T> action, @NonNull T parameter) {
        try {
            System.out.println("[LAUNCH] "+message);
            action.accept(parameter);
            System.out.println("[DONE  ] "+message);
        } catch (Throwable e) {
            System.err.println("[FAILED] "+message);
            e.printStackTrace();
        }
    }

    @Override
    public void onFileCreated(@NonNull Path path) {
        log("FolderListenerLogger.onFileCreated("+path+")",delegate::onFileCreated,path);
    }

    @Override
    public void onFileModified(@NonNull Path path) {
        log("FolderListenerLogger.onFileModified("+path+")",delegate::onFileModified,path);
    }

    @Override
    public void onFileDeleted(@NonNull Path path) {
        log("FolderListenerLogger.onFileDeleted("+path+")",delegate::onFileDeleted,path);
    }

    @Override
    public void onStart(ImmutableSet<Path> paths) {
        log("FolderListenerLogger.onStart",delegate::onStart,paths);
    }
}
