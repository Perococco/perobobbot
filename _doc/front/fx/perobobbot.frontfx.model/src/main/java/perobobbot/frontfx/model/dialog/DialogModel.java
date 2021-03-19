package perobobbot.frontfx.model.dialog;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.NonNull;
import perococco.perobobbot.frontfx.model.dialog.PeroDialogModel;

/**
 */
public interface DialogModel extends ReadOnlyDialogModel {

    @NonNull
    static DialogModel create() {
        return new PeroDialogModel();
    }

    @Override
    @NonNull
    ObjectProperty<Scene> mainSceneProperty();

    default void setMainScene(Scene scene) {
        mainSceneProperty().set(scene);
    }

    @Override
    @NonNull
    ObjectProperty<Stage> primaryStageProperty();

    default void setPrimaryStage(Stage stage) {
        primaryStageProperty().set(stage);
    }

    @NonNull
    ObservableMap<DialogKind, Stage> getDialogStages();

    default void setDialogData(@NonNull DialogKind dialogKind, @NonNull Stage stage) {
        getDialogStages().put(dialogKind, stage);
    }

    default void clearDialogStage(@NonNull DialogKind dialogKind) {
        getDialogStages().put(dialogKind, null);
    }
}
