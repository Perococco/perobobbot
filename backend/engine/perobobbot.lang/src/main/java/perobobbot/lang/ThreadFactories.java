package perobobbot.lang;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.NonNull;

import java.util.concurrent.ThreadFactory;

public interface ThreadFactories {

    static ThreadFactory daemon(@NonNull String nameFormat) {
        return new ThreadFactoryBuilder().setDaemon(true)
                                         .setNameFormat(nameFormat)
                                         .build();
    }

}
