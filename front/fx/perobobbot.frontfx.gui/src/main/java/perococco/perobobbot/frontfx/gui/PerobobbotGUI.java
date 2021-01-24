package perococco.perobobbot.frontfx.gui;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import perobobbot.frontfx.i18n.PerobobbotDictionary;
import perobobbot.frontfx.model.ApplicationIdentity;
import perobobbot.frontfx.model.dialog.DefaultDialogHelper;
import perobobbot.frontfx.model.dialog.DialogHelper;
import perobobbot.frontfx.model.dialog.DialogModel;
import perobobbot.frontfx.model.state.ActionState;
import perobobbot.frontfx.model.state.ApplicationState;
import perobobbot.frontfx.model.state.StyleState;
import perobobbot.fx.FXDictionary;
import perobobbot.fx.FXProperties;
import perobobbot.fx.KeyTracker;
import perobobbot.fx.LocaleProperty;
import perobobbot.fx.dialog.AlertShower;
import perobobbot.fx.dialog.DefaultDialogPreparer;
import perobobbot.i18n.Dictionary;
import perococco.perobobbot.frontfx.gui.style.StyleConfiguration;

@SpringBootConfiguration
@ComponentScan
@RequiredArgsConstructor
public class PerobobbotGUI  {

    @NonNull
    private final StyleConfiguration styleConfiguration;

    @NonNull
    private final FXProperties fxProperties;

    @Bean
    public @NonNull DialogModel getDialogModel() {
        return DialogModel.create();
    }

    @Bean
    public @NonNull ApplicationIdentity applicationIdentity() {
        return ApplicationIdentity.with(initialApplicationState());
    }

    @Bean
    @NonNull
    public Dictionary dictionary() {
        return PerobobbotDictionary.INSTANCE;
    }

    @Bean
    @NonNull
    public LocaleProperty localeProperty() {
        return new LocaleProperty();
    }

    @Bean
    @NonNull
    public KeyTracker keyTracker() {
        return KeyTracker.create();
    }

    @Bean
    @NonNull
    public FXDictionary fxDictionary() {
        return localeProperty().wrapDictionary(dictionary());
    }

    @Bean
    @NonNull
    public AlertShower alertShower() {
        return AlertShower.create(dictionary());
    }


    @Bean
    @NonNull
    public DialogHelper dialogHelper() {
        return new DefaultDialogHelper(fxProperties,dictionary(),getDialogModel(),styleConfiguration.styleManager(),new DefaultDialogPreparer());
    }

    @NonNull
    private ApplicationState initialApplicationState() {
        return ApplicationState.builder()
                               .actionState(ActionState.allEnabled())
                               .styleState(initialStyleState())
                               .build();
    }


    private StyleState initialStyleState() {
        final var themes = styleConfiguration.getThemes();
        final var selectedTheme = styleConfiguration.getInitialTheme();
        return StyleState.builder()
                         .themes(themes)
                         .nameOfSelectedTheme(selectedTheme.getName())
                         .build();
    }

}
