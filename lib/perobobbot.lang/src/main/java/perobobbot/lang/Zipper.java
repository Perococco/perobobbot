package perobobbot.lang;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.fp.Consumer1;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Zipper {

    public static Consumer1<ZipOutputStream> zipper(@NonNull Path referencePath) {
        return z -> new Zipper(referencePath, referencePath, z).zip();
    }

    private final @NonNull Path referencePath;
    private final @NonNull Path sourcePath;
    private final @NonNull ZipOutputStream zipOutputStream;

    private void zip() {
        try {
            if (!sourcePath.startsWith(referencePath)) {
                throw new IllegalArgumentException("Invalid paths");
            }
            Files.walk(sourcePath)
                 .forEach(this::putEntry);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void putEntry(@NonNull Path path) {
        try {
            final var rel = referencePath.relativize(path).toString();
            if (rel.isEmpty()) {
                return;
            }
            if (!Files.isDirectory(path)) {
                zipOutputStream.putNextEntry(new ZipEntry(rel));
                Files.copy(path, zipOutputStream);
            } else {
                zipOutputStream.putNextEntry(new ZipEntry(rel+"/"));
            }
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
