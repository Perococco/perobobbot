package perobobbot.frontfx.gui;


import javafx.stage.Stage;
import lombok.NonNull;
import perobobbot.fxspring.FXSpringApplication;
import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;
import perococco.perobobbot.frontfx.gui.PerobobbotGUI;

import java.util.Optional;

public class BotGui extends FXSpringApplication {

    public static void main(String[] args) {
        launch(args);
    }


    public BotGui() {
        super(PerobobbotGUI.class);
    }

    @Override
    protected void beforeLaunchingSpring(@NonNull Stage primaryStage) throws Exception {
        final var properties = PerobobbotProperties.create();
        primaryStage.setTitle(properties.getTitle());
    }

    @Override
    protected @NonNull boolean shouldUsePlugin(@NonNull Plugin plugin) {
        if (plugin.type() == PluginType.FRONT_FX) {
            return true;
        }
        return false;
    }
}
