package perobobbot.lang;

import java.lang.annotation.*;

@Repeatable(PluginServices.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface PluginService {

    int apiVersion();

    Class<?> type();
}
