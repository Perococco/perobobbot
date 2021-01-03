package perobobbot.localio;

import lombok.NonNull;

public interface LocalExecutor {

    void handleMessage(@NonNull String line);
}
