package perobobbot.lang;

import static perobobbot.lang.ApplicationCloser.VERSION;

/**
 * @author perococco
 */
@PluginService(type = ApplicationCloser.class, apiVersion = VERSION, sensitive = false)
public interface ApplicationCloser {

    int VERSION = 1;

    void execute();

}
