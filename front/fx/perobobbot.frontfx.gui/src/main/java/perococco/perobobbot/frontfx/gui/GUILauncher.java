package perococco.perobobbot.frontfx.gui;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import perobobbot.action.ActionManager;
import perobobbot.frontfx.action.list.Quit;
import perobobbot.frontfx.model.ApplicationIdentity;
import perobobbot.frontfx.model.dialog.DialogKind;
import perobobbot.frontfx.model.dialog.DialogModel;
import perobobbot.frontfx.model.state.mutation.ChangeView;
import perobobbot.frontfx.model.view.EmptyFXView;
import perobobbot.fx.*;
import perobobbot.lang.ThrowableTool;
import perococco.perobobbot.frontfx.gui.fxml.MainWindowController;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Log4j2
public class GUILauncher implements ApplicationRunner {

    @Qualifier("mainRoot")
    private final @NonNull KeyCatcher mainRootKeyCatcher;
    private final @NonNull KeyTracker keyTracker;
    private final @NonNull DialogModel dialogModel;
    private final @NonNull StyleManager styleManager;
    private final @NonNull FXProperties fxProperties;
    private final @NonNull ActionManager actionManager;
    private final @NonNull FXLoaderFactory fxLoaderFactory;
    private final @NonNull ApplicationIdentity applicationIdentity;


    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        final CompletableFuture<Void> future = new CompletableFuture<>();
        Platform.runLater(() -> {
            try {
                runInFX(applicationArguments);
                future.complete(null);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        future.get();

    }

    private void runInFX(@NonNull ApplicationArguments applicationArguments) {
        fxProperties.getPrimaryStage().setOnHiding(e ->  {
            try {
                e.consume();
                actionManager.pushAction(Quit.class);
            } catch (Exception ex) {
                ThrowableTool.interruptThreadIfCausedByInterruption(ex);
                LOG.warn("Fail on hiding {}",ex.getMessage());
            }
        });
        keyTracker.attach(fxProperties.getPrimaryStage());
        dialogModel.setDialogData(DialogKind.PRIMARY, fxProperties.getPrimaryStage());
        dialogModel.setPrimaryStage(fxProperties.getPrimaryStage());
        displayMainWindow();
    }


    private void displayMainWindow() {
        final FXLoader fxLoader = fxLoaderFactory.create(MainWindowController.class);
        final FXLoadingResult result = fxLoader.load();
        final Parent root = result.getRoot();

        keyTracker.addKeyCatcher(root,mainRootKeyCatcher);
        final Scene scene = new Scene(root);

        dialogModel.setMainScene(scene);
        styleManager.addStylable(scene);

        applicationIdentity.mutate(new ChangeView(EmptyFXView.class));
        fxProperties.getPrimaryStage().setScene(scene);
        fxProperties.getPrimaryStage().centerOnScreen();
        fxProperties.getPrimaryStage().show();
    }

}
