package perobobbot.server.config.io;

import lombok.NonNull;
import perobobbot.lang.Platform;

public interface Rejoiner {

    void rejoinChannels(@NonNull Platform platform);
}
