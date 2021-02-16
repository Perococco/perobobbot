package perococco.perobobbot.frontfx.gui;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import perobobbot.frontfx.i18n.PerobobbotDictionary;
import perobobbot.frontfx.model.ApplicationIdentity;
import perobobbot.frontfx.model.dialog.DefaultDialogHelper;
import perobobbot.frontfx.model.dialog.DialogHelper;
import perobobbot.frontfx.model.dialog.DialogModel;
import perobobbot.frontfx.model.state.ActionState;
import perobobbot.frontfx.model.state.ApplicationState;
import perobobbot.frontfx.model.state.Configuration;
import perobobbot.frontfx.model.state.StyleState;
import perobobbot.frontfx.model.view.EmptyFXView;
import perobobbot.fx.FXDictionary;
import perobobbot.fx.FXProperties;
import perobobbot.fx.KeyTracker;
import perobobbot.fx.LocaleProperty;
import perobobbot.fx.dialog.AlertShower;
import perobobbot.fx.dialog.DefaultDialogPreparer;
import perobobbot.fx.dialog.ValidatableField;
import perobobbot.i18n.Dictionary;
import perococco.perobobbot.frontfx.gui.style.StyleConfiguration;

import java.net.URI;

@SpringBootConfiguration
@ComponentScan
@PropertySources({
        @PropertySource(value = "${app.config.dir}/server/application.properties", ignoreResourceNotFound = true),
        @PropertySource("classpath:/configuration.properties")
})
@RequiredArgsConstructor
public class PerobobbotGUI {


    @Value("${server.uri}")
    private final URI serverUri;

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
        return new DefaultDialogHelper(fxProperties, dictionary(), getDialogModel(), styleConfiguration.styleManager(),
                                       new DefaultDialogPreparer());
    }

    @NonNull
    private ApplicationState initialApplicationState() {
        return ApplicationState.builder()
                               .fxViewType(EmptyFXView.class)
                               .actionState(ActionState.allEnabled())
                               .configuration(initialConfiguration())
                               .styleState(initialStyleState())
                               .build();
    }

    private @NonNull Configuration initialConfiguration() {
        return new Configuration(serverUri);
    }


    private @NonNull StyleState initialStyleState() {
        final var themes = styleConfiguration.getThemes();
        final var selectedTheme = styleConfiguration.getInitialTheme();
        return StyleState.builder()
                         .themes(themes)
                         .nameOfSelectedTheme(selectedTheme.getName())
                         .build();
    }

}
