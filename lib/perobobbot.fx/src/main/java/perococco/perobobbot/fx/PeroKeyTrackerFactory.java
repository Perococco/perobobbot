package perococco.perobobbot.fx;

import lombok.NonNull;
import perobobbot.fx.KeyTracker;
import perobobbot.fx.KeyTrackerFactory;
import perobobbot.lang.Priority;

@Priority(Integer.MIN_VALUE)
public class PeroKeyTrackerFactory implements KeyTrackerFactory {

    @Override
    public @NonNull KeyTracker create() {
        return new PeroKeyTracker();
    }

}
