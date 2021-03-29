package perobobbot.lang;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface PluginService {

    int apiVersion();

    Class<?> type();
}
