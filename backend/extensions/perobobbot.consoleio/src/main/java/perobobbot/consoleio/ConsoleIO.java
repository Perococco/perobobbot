package perobobbot.consoleio;

import lombok.NonNull;
import perobobbot.common.lang.PlatformIO;

public interface ConsoleIO extends PlatformIO {

    @NonNull
    ConsoleIO enable();

    void disable();
}
