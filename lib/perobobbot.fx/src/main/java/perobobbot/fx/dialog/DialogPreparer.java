package perobobbot.fx.dialog;

import javafx.stage.Stage;
import lombok.NonNull;

public interface DialogPreparer {

    <O> void setup(@NonNull Stage dialogStage,
                   @NonNull DialogInfo<O> dialogInfo);
}
