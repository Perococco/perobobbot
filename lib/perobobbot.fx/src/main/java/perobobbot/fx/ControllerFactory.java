package perobobbot.fx;

import javafx.util.Callback;
import lombok.NonNull;

public interface ControllerFactory {

    @NonNull
    Object getController(@NonNull Class<?> controllerType) throws Exception;

    @NonNull
    static ControllerFactory withNewInstance() {
        return controllerType -> controllerType.getDeclaredConstructor().newInstance();
    }


    @NonNull
    default Callback<Class<?>,Object> asCallbackForFXMLLoader() {
        return c -> {
            try {
                return getController(c);
            } catch (Exception e) {
                throw new RuntimeException("Could not instantiate controller for class : " + c, e);
            }
        };
    }
}
