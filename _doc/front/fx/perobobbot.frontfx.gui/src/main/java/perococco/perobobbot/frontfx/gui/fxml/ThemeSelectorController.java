package perococco.perobobbot.frontfx.gui.fxml;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.action.ActionExecutor;
import perobobbot.frontfx.action.list.ApplyTheme;
import perobobbot.frontfx.i18n.PerobobbotDictionary;
import perobobbot.frontfx.model.FXApplicationIdentity;
import perobobbot.frontfx.model.state.ApplicationStateTool;
import perobobbot.frontfx.model.state.StyleState;
import perobobbot.fx.dialog.AbstractDialogController;
import perobobbot.lang.Nil;
import perobobbot.lang.Subscription;

@FXMLController
@RequiredArgsConstructor
public class ThemeSelectorController extends AbstractDialogController<Nil,Nil> {
    public Button applyThemeButton;
    public ComboBox<String> themeList;

    private final @NonNull FXApplicationIdentity applicationIdentity;

    private final @NonNull ActionExecutor actionExecutor;

    private final ObjectProperty<StyleState> styleState = new SimpleObjectProperty<>(null);

    public void initialize() {
        styleState.addListener(l -> onStyleStateChange());
        applyThemeButton.disableProperty().bind(styleState.isNull().or(themeList.valueProperty().isNull()));
    }

    @Override
    protected @NonNull String getInitialTitleDialog() {
        return PerobobbotDictionary.INSTANCE.value("theme-dialog.title");
    }

    @Override
    public @NonNull Subscription initializeDialog(@NonNull Nil input) {
        this.styleState.bind(applicationIdentity.binding(ApplicationStateTool::getStyleState));
        return this.styleState::unbind;
    }

    private void onStyleStateChange() {
        final var state = styleState.get();
        if (state == null) {
            themeList.getItems().clear();
        } else {
            themeList.getItems().setAll(state.getThemes().keySet());
            themeList.getSelectionModel().select(state.getNameOfSelectedTheme());
        }
    }

    public void applyTheme() {
        final var state = styleState.get();
        final String selectedTheme = themeList.getValue();
        if (state == null || selectedTheme == null) {
            return;
        }
        actionExecutor.pushAction(ApplyTheme.class, selectedTheme);
    }
}
