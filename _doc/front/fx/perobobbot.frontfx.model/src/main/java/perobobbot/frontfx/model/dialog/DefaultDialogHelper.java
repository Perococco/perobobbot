package perobobbot.frontfx.model.dialog;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.fx.FXLoader;
import perobobbot.fx.FXLoadingResult;
import perobobbot.fx.FXProperties;
import perobobbot.fx.StyleManager;
import perobobbot.fx.dialog.DialogController;
import perobobbot.fx.dialog.DialogInfo;
import perobobbot.fx.dialog.DialogInfoExtractor;
import perobobbot.fx.dialog.DialogPreparer;
import perobobbot.i18n.Dictionary;
import perobobbot.lang.fp.Function1;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class DefaultDialogHelper implements DialogHelper {

    @NonNull
    private final FXProperties fxProperties;

    @NonNull
    private final Dictionary dictionary;

    @NonNull
    private final DialogModel dialogModel;

    @NonNull
    private final StyleManager styleManager;

    @NonNull
    private final DialogPreparer dialogPreparer;


    @Override
    public @NonNull <I, O, C extends DialogController<I, O>> CompletionStage<Optional<O>> showDialog(
            @NonNull DialogKind dialogKind,
            @NonNull Class<C> controllerClass,
            @NonNull Function1<? super Class<C>, ? extends FXLoader> loaderFactory,
            @NonNull I input) {
        final FXLoadingResult result = loaderFactory.apply(controllerClass).load();
        final C controller = result.getController(controllerClass).orElseThrow(() -> new RuntimeException("Invalid controller class "+controllerClass));
        return showDialog(dialogKind,controller,result.getRoot(),input);
    }

    private <I, O> CompletionStage<Optional<O>> showDialog(
            @NonNull DialogKind dialogKind,
            @NonNull DialogController<I,O> controller,
            @NonNull Parent root,
            @NonNull I input
    ) {

        final CompletableFuture<Optional<O>> result = new CompletableFuture<>();
        try {

            final DialogInfo<O> dialogInfo = DialogInfoExtractor.extract(dictionary, controller);

            controller.initializeDialog(input);

            final Window owner = dialogKind.getOwnerStage(this.dialogModel.getUnmodifiableDialogStages()).orElse(null);

            final Stage stage = new Stage();
            stage.initModality(dialogKind.getModality());
            stage.initOwner(owner);
            stage.initStyle(dialogKind.getStageStyle());
            stage.setAlwaysOnTop(dialogKind.isAlwaysOnTop());
            stage.getIcons().setAll(fxProperties.getPrimaryStage().getIcons());
            dialogModel.setDialogData(dialogKind, stage);

            final Scene scene = new Scene(root);
            styleManager.addStylable(scene);
            stage.setScene(scene);
            stage.addEventHandler(WindowEvent.WINDOW_HIDDEN, e -> {
                clearStage(dialogKind);
                result.complete(controller.getDialogState().getValue());
            });
            dialogPreparer.setup(stage, dialogInfo);


            if (dialogKind.isShowAndWait()) {
                stage.showAndWait();
            } else {
                stage.show();
            }

            return result;
        } catch (Exception e) {
            clearStage(dialogKind);
            throw e;
        }

    }


    @Override
    public <I> void showDialog(
            @NonNull DialogKind dialogKind,
            @NonNull FXLoader loader,
            @NonNull I input
    ) {
        final Stage current = this.dialogModel.getDialogStage(dialogKind).orElse(null);

        if (current != null) {
            if (current.isIconified()) {
                current.setIconified(false);
            }
            current.requestFocus();
            return;
        }

        final FXLoadingResult result = loader.load();
        final DialogController<I, Object> controller = result.getController();
        showDialog(dialogKind, controller, result.getRoot(), input);

    }

    private void clearStage(@NonNull DialogKind dialogKind) {
        dialogModel.clearDialogStage(dialogKind);
    }
}
