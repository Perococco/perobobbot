package perobobbot.lang;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipOutputStream;

public class ZipperTest {

    public static void main(String[] args) throws IOException {
        try(var zipOutputStream = new ZipOutputStream(Files.newOutputStream(Path.of("/home/perococco/tmp/toto.zip")))){
            Zipper.zipper(Path.of("/tmp/perobobbot_plugin1138787586717096247")).accept(zipOutputStream);
        }


    }
}
