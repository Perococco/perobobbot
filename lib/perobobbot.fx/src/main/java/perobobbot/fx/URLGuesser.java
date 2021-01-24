package perobobbot.fx;

import lombok.NonNull;

import java.net.URL;

public class URLGuesser {

    public static final String CONTROLLER_SUFFIX = "Controller";

    @NonNull
    public URL guessFromController(@NonNull Class<?> controllerType) {
        if (controllerType.getSimpleName().endsWith(CONTROLLER_SUFFIX)) {
            return extractFromClassName(controllerType);
        }
        throw new RuntimeException("Could not guess URL from "+controllerType);
    }

    private URL extractFromClassName(Class<?> controllerType) {
        final String className = controllerType.getSimpleName();
        final String resourceName = className.substring(0,className.length()-CONTROLLER_SUFFIX.length())+".fxml";
        final URL url = controllerType.getResource(resourceName);
        return url;
    }
}
