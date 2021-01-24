package perobobbot.frontfx.gui;


import javafx.stage.Stage;
import lombok.NonNull;
import perobobbot.fxspring.FXSpringApplication;
import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;
import perococco.perobobbot.frontfx.gui.PerobobbotGUI;

import java.util.Optional;

public class Launcher extends FXSpringApplication {

    public static void main(String[] args) {
        launch(args);
    }


    public Launcher() {
        super(PerobobbotGUI.class);
    }

    @Override
    protected void beforeLaunchingSpring(@NonNull Stage primaryStage) throws Exception {
        primaryStage.setTitle("Perobobbot");
    }

    @Override
    protected @NonNull Optional<Plugin> processPluginBeforeIncludingThemIntoSpring(@NonNull Plugin plugin) {
        if (plugin.type() == PluginType.FRONT_FX) {
            return Optional.of(plugin);
        }
        return Optional.empty();
    }
}
