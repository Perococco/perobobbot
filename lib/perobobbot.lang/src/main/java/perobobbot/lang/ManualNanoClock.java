package perobobbot.lang;

import lombok.Setter;

public class ManualNanoClock implements NanoClock {

    @Setter
    private long currentTime = 0;

    @Override
    public long nanoTime() {
        return currentTime;
    }
}
