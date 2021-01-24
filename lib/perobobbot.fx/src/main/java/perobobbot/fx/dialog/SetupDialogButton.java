package perobobbot.fx.dialog;

import javafx.beans.value.ObservableBooleanValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.NonNull;

public class SetupDialogButton implements DialogPreparer {

    @Override
    public <O> void setup(@NonNull Stage dialogStage,
                          @NonNull DialogInfo<O> dialogInfo
    ) {
        final DialogController<?, O> controller = dialogInfo.getDialogController();
        final ObservableBooleanValue invalid = controller.invalidPropertyProperty();
        final EventHandler<Event> cancelHandler = cancelledHandler(dialogStage,controller);
        final EventHandler<Event> applyHandler = resultHandler(dialogStage,controller,true);
        final EventHandler<Event> validateHandler = resultHandler(dialogStage,controller,false);

        dialogInfo.getApplyButton().ifPresent(b -> b.disableProperty().bind(invalid));
        dialogInfo.getValidateButton().ifPresent(b -> b.disableProperty().bind(invalid));

        dialogStage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                cancelHandler.handle(keyEvent);
            }
        });

        dialogStage.setOnCloseRequest(windowEvent -> {
            if (controller.requestPermissionToClose()) {
                dialogStage.close();
                controller.onCancelled();
            } else {
                // if we are here, we consume the event to prevent closing the dialog
                windowEvent.consume();
            }
        });




        dialogInfo.getCancelButton().ifPresent(b -> b.setOnAction(cancelHandler::handle));
        dialogInfo.getValidateButton().ifPresent(b -> b.setOnAction(validateHandler::handle));
        dialogInfo.getApplyButton().ifPresent(b -> b.setOnAction(applyHandler::handle));

    }

    private <O> EventHandler<Event> cancelledHandler(@NonNull Stage dialogStage, @NonNull DialogController<?,O> controller) {
        return e -> {
            if (!e.isConsumed() && controller.requestPermissionToClose()) {
                e.consume();
                dialogStage.close();
                controller.onCancelled();
            }
        };
    }

    private <O> EventHandler<Event> resultHandler(@NonNull Stage dialogStage, @NonNull DialogController<?,O> controller, boolean isApply) {
        return e -> {
            if (!e.isConsumed()) {
                e.consume();
                controller.getDialogState().getValue().ifPresent(v -> {
                    e.consume();
                    if (isApply) {
                        controller.onApplied(v);
                    } else {
                        dialogStage.close();
                        controller.onValidated(v);
                    }
                });
            }
        };
    }
}
