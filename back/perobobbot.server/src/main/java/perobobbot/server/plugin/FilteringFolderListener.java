package perobobbot.server.plugin;

import com.google.common.collect.ImmutableSet;
import jplugman.tools.FolderListener;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.FileTool;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class FilteringFolderListener implements FolderListener {

    private final MessageDigest messageDigest;

    private final @NonNull FolderListener delegate;

    private final Map<Path,String> checksums = new HashMap<>();

    @Override
    public void onFileCreated(@NonNull Path path) {
        final var checksum= computeChecksum(path);
        checksums.put(path,checksum);
        delegate.onFileCreated(path);
    }

    @Override
    public void onFileModified(@NonNull Path path) {
        final var checksum= computeChecksum(path);
        final var oldChecksum = this.checksums.put(path,checksum);
        if (checksum.equals(oldChecksum)) {
            return;
        }
        delegate.onFileModified(path);
    }

    @Override
    public void onFileDeleted(@NonNull Path path) {
        this.checksums.remove(path);
        this.delegate.onFileDeleted(path);
    }

    @Override
    public void onStart(ImmutableSet<Path> paths) {
        paths.forEach(p -> checksums.put(p, computeChecksum(p)));
        delegate.onStart(paths);
    }

    private String computeChecksum(@NonNull Path path) {
        try {
            return FileTool.computeFileChecksum(messageDigest,path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
