package perobobbot.lang;

import lombok.NonNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;

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


    public static @NonNull String computeFileChecksum(MessageDigest digest, Path path) throws IOException
    {
        final byte[] buffer=  new byte[1024];

        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(path))) {
            int nbRead = 0;
            do {
                nbRead = bis.read(buffer, 0, buffer.length);
                if (nbRead > 0) {
                    digest.update(buffer, 0, nbRead);
                }
            } while (nbRead>=0);
        }

        //Get the hash's bytes
        byte[] bytes = digest.digest();

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        //return complete hash
        return sb.toString();
    }


}
