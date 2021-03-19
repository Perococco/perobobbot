package perobobbot.lang;

import static perobobbot.lang.ApplicationCloser.VERSION;

/**
 * @author Perococco
 */
@PluginService(type = ApplicationCloser.class,version = VERSION)
public interface ApplicationCloser {

    String VERSION = "1.0.0";

    void execute();

}
