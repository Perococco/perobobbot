package bot.common.lang;

import lombok.NonNull;
import perococco.bot.common.lang.DoubleHeaderPrinter;
import perococco.bot.common.lang.SingleHeaderPrinter;
import perococco.bot.common.lang.ToFilePrinter;

import java.io.Closeable;
import java.nio.file.Path;

public interface Printer extends Closeable {

    static Printer toFile(@NonNull Path file) {
        return new ToFilePrinter(file);
    }

    static Printer toFile(@NonNull String filePath) {
        return toFile(Path.of(filePath));
    }

    @NonNull
    Printer println(@NonNull String value);

    void close();

    @NonNull
    default Printer println(@NonNull Object value) {
        return println(value.toString());
    }

    @NonNull
    default Printer println() {
        return println("");
    }

    default Printer withHeader(@NonNull String first, @NonNull String after) {
        return new DoubleHeaderPrinter(this, first, after);
    }

    default Printer withHead(@NonNull String header) {
        return new SingleHeaderPrinter(this,header);
    }
}
