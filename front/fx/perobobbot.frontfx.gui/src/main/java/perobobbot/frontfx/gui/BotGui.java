package perobobbot.frontfx.gui;


import javafx.stage.Stage;
import lombok.NonNull;
import perobobbot.fxspring.FXSpringApplication;
import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;
import perococco.perobobbot.frontfx.gui.PerobobbotGUI;
import perococco.perobobbot.frontfx.gui.assets.Resource;

import java.util.Optional;
import java.util.stream.Collectors;

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
        this.initializeIconsOfThePrimaryStage(primaryStage);
        primaryStage.setTitle(properties.getTitle());
    }

    private void initializeIconsOfThePrimaryStage(@NonNull Stage primaryStage) {
        final var icons = Resource.streamFilteredByName(n -> n.startsWith("logo_"))
                                  .map(Resource::getAsFXImage)
                                  .collect(Collectors.toList());

        primaryStage.getIcons().setAll(icons);
    }


    @Override
    protected @NonNull boolean shouldUsePlugin(@NonNull Plugin plugin) {
        return plugin.type() == PluginType.FRONT_FX;
    }
}
