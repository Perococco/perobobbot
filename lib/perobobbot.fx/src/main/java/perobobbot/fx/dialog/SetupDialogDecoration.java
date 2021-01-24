package perobobbot.fx.dialog;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SetupDialogDecoration implements DialogPreparer {

    @Override
    public <O> void setup(@NonNull Stage dialogStage, @NonNull DialogInfo<O> dialogInfo) {
        dialogInfo.resultProperty().addListener((l, o, n) -> {
            dialogInfo.updateDecoration(n.getValidationResult());
        });

        dialogStage.addEventHandler(WindowEvent.WINDOW_SHOWN,
                e -> dialogInfo.updateDecoration(dialogInfo.getDialogController().getDialogState().getValidationResult())
                );

    }

}
