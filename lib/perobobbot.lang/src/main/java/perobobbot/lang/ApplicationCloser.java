package perobobbot.lang;

import static perobobbot.lang.ApplicationCloser.VERSION;

/**
 * @author Perococco
 */
@PluginService(type = ApplicationCloser.class, apiVersion = VERSION)
public interface ApplicationCloser {

    int VERSION = 1;

    void execute();

}
