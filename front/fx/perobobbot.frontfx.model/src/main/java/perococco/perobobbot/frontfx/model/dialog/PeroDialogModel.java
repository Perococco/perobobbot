package perococco.perobobbot.frontfx.model.dialog;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.stage.Stage;
import perobobbot.frontfx.model.dialog.DialogKind;
import perobobbot.frontfx.model.dialog.DialogModel;

/**
 */
public class PeroDialogModel implements DialogModel {

    private final ObjectProperty<Scene> mainScene = new SimpleObjectProperty<>();
    private final ObjectProperty<Stage> primaryStage = new SimpleObjectProperty<>();
    private final ObservableMap<DialogKind, Stage> dialogStages = FXCollections.observableHashMap();
    private final ObservableMap<DialogKind, Stage> unmodifiableDialogStages = FXCollections.unmodifiableObservableMap(dialogStages);

    @Override
    public ObjectProperty<Scene> mainSceneProperty() {
        return mainScene;
    }

    @Override
    public ObjectProperty<Stage> primaryStageProperty() {
        return primaryStage;
    }

    @Override
    public ObservableMap<DialogKind, Stage> getDialogStages() {
        return this.dialogStages;
    }

    @Override
    public ObservableMap<DialogKind, Stage> getUnmodifiableDialogStages() {
        return unmodifiableDialogStages;
    }

}
