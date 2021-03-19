package perobobbot.frontfx.model.dialog;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.NonNull;

import java.util.Map;
import java.util.Optional;

public enum DialogKind {
    PRIMARY,
    ALERT,
    CREATE_WORKSPACE,
    LOAD_MEASUREMENT,
    THEME_SELECTOR(Modality.NONE);


    @Getter
    private final Modality modality;

    DialogKind(@NonNull Modality modality) {
        this.modality = modality;
    }

    DialogKind() {
        this(Modality.WINDOW_MODAL);
    }

    @NonNull
    public Optional<DialogKind> getOwner() {
        return Optional.of(PRIMARY);
    }

    @NonNull
    public boolean isShowAndWait() {
        return getModality() != Modality.NONE;
    }

    @NonNull
    public Optional<Stage> getOwnerStage(Map<DialogKind, Stage> dialogStages) {
        final DialogKind ownerKind = this.getOwner().orElse(null);
        if (ownerKind == null) {
            return Optional.empty();
        }
        final Stage owner = dialogStages.get(ownerKind);
        if (owner != null) {
            return Optional.of(owner);
        }
        else {
            return ownerKind.getOwnerStage(dialogStages);
        }
    }

    public boolean isAlwaysOnTop() {
        return this == ALERT || this == THEME_SELECTOR;
    }

    @NonNull
    public StageStyle getStageStyle() {
        return StageStyle.DECORATED;
    }
}
