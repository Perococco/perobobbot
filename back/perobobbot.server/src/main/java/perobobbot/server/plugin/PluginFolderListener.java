package perobobbot.server.plugin;

import com.google.common.collect.ImmutableSet;
import jplugman.manager.PluginManager;
import jplugman.tools.FolderListener;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import perobobbot.chat.advanced.Message;
import perobobbot.lang.FileTool;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class PluginFolderListener implements FolderListener {

    private final MessageDigest messageDigest;

    private final @NonNull PluginManager pluginManager;

    private final Map<Path,String> checksums = new HashMap<>();

    @Override
    public void onFileCreated(@NonNull Path path) {
        this.pluginManager.addPluginBundle(path);
    }

    @Override
    public void onFileModified(@NonNull Path path) {
        final var checksum= computeChecksum(path);
        if (checksum.equals(this.checksums.get(path))) {
            return;
        }
        this.pluginManager.removePluginBundle(path);
        this.addPluginBundle(path,checksum);
    }

    private void addPluginBundle(@NonNull Path path) {
        this.addPluginBundle(path,computeChecksum(path));
    }

    private void addPluginBundle(@NonNull Path path, @NonNull String checksum) {
        this.pluginManager.addPluginBundle(path);
        this.checksums.put(path,checksum);
    }

    @Override
    public void onFileDeleted(@NonNull Path path) {
        this.checksums.remove(path);
        this.pluginManager.removePluginBundle(path);
    }

    @Override
    public void onStart(ImmutableSet<Path> paths) {
        paths.forEach(this::addPluginBundle);
    }



    private String computeChecksum(@NonNull Path path) {
        try {
            return FileTool.computeFileChecksum(messageDigest,path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
