package perobobbot.fx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.NonNull;
import perobobbot.lang.Theme;
import perococco.perobobbot.fx.SimpleStyleManager;

public interface StyleManager {

    @NonNull
    static StyleManager simple() {
        return new SimpleStyleManager();
    }

    @NonNull
    Scene addStylable(@NonNull Scene scene);

    @NonNull
    void addStylable(@NonNull Parent parent);

    void applyTheme(@NonNull Theme theme);

}
