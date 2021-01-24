package perobobbot.fx;

import lombok.NonNull;
import perobobbot.lang.ServiceLoaderHelper;

import java.util.ServiceLoader;

public interface KeyTrackerFactory {

    @NonNull
    KeyTracker create();

    @NonNull
    static KeyTrackerFactory getInstance() {
        return Holder.INSTANCE;
    }

    class Holder {
        private static final KeyTrackerFactory INSTANCE = ServiceLoaderHelper.getService(ServiceLoader.load(KeyTrackerFactory.class));
    }
}
