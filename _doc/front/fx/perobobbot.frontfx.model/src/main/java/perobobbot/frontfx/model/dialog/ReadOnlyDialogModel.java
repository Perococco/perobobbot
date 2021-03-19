package perobobbot.frontfx.model.dialog;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.NonNull;

import java.util.Optional;

/**
 */
public interface ReadOnlyDialogModel {

    default Scene getMainScene() {
        return mainSceneProperty().get();
    }

    ReadOnlyObjectProperty<Scene> mainSceneProperty();

    default Stage getPrimaryStage() {
        return primaryStageProperty().get();
    }

    ReadOnlyObjectProperty<Stage> primaryStageProperty();


    ObservableMap<DialogKind, Stage> getUnmodifiableDialogStages();

    @NonNull
    default Optional<Stage> getDialogStage(DialogKind dialogKind) {
        return Optional.ofNullable(getUnmodifiableDialogStages().get(dialogKind));
    }


}
