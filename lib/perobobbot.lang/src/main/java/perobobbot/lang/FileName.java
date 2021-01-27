package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;

import java.nio.file.Path;

@Value
public class FileName {

    public static @NonNull FileName fromName(@NonNull String filename) {
        int idx = filename.lastIndexOf(".");
        if (idx<0) {
            return new FileName(filename,filename,"");
        }
        if (idx == filename.length()-1) {
            return new FileName(filename,filename.substring(0,idx),"");
        }
        final var extension = filename.substring(idx+1);
        final var nameWithoutExtension = filename.substring(0,idx);
        return new FileName(filename,nameWithoutExtension,extension);
    }

    public static @NonNull FileName fromPath(@NonNull Path path) {
        return fromName(path.getFileName().toString());
    }

    @NonNull String name;
    @NonNull String nameWithoutExtension;
    @NonNull String extension;
}
