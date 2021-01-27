package perobobbot.lang;

import lombok.NonNull;

import java.nio.file.Path;

public class FileTool {


    public static @NonNull FileName decomposeFilename(@NonNull Path path) {
        return decomposeFilename(path.getFileName().toString());
    }

    public static @NonNull FileName decomposeFilename(@NonNull String filename) {
        int idx = filename.lastIndexOf(".");
        if (idx<0) {
            return new FileName(filename,filename,"");
        }
        if (idx == filename.length()-1) {
            return new FileName(filename,filename.substring(0,idx),"");
        }
        return new FileName(filename,filename.substring(0,idx),filename.substring(idx+1));
    }
}
