package perococco.perobobbot.frontfx.gui.dialog;

import javafx.application.Platform;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import perobobbot.frontfx.model.dialog.DialogHelper;
import perobobbot.frontfx.model.dialog.DialogManager;
import perobobbot.frontfx.model.dialog.DialogModel;
import perobobbot.fx.FXDictionary;
import perobobbot.fx.FXLoaderFactory;
import perobobbot.fx.dialog.AlertInfo;
import perobobbot.fx.dialog.AlertShower;
import perobobbot.i18n.Dictionary;
import perobobbot.lang.AsyncCallback;
import perobobbot.lang.ThrowableTool;
import perobobbot.lang.Todo;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Component
@RequiredArgsConstructor
@Log4j2
public class DefaultDialogManager implements DialogManager {

    @NonNull
    private final Dictionary dictionary;

    @NonNull
    private final FXDictionary fxDictionary;

    @NonNull
    private final DialogHelper dialogHelper;

    @NonNull
    private final DialogModel dialogModel;

    @NonNull
    private final FXLoaderFactory loaderFactory;

    @NonNull
    private final AlertShower alertShower;

    @Override
    public void showThemeSelector() {
        Todo.TODO();
    }

    @NonNull
    private <T> CompletionStage<T> runLater(@NonNull Callable<T> callable) {
        final CompletableFuture<T> future = new CompletableFuture<>();
        Platform.runLater(() -> {
            try {
                future.complete(callable.call());
            } catch (Exception e) {
                LOG.error("Fail to show dialog",e);
                ThrowableTool.interruptThreadIfCausedByInterruption(e);
                alertShower.showAlertAndWait(new AlertInfo(e, "error"));
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    @NonNull
    private <T> CompletionStage<T> runLater(@NonNull AsyncCallback<T> callable) {
        final CompletableFuture<T> future = new CompletableFuture<>();
        Platform.runLater(() -> {
            try {
                callable.call().whenComplete((r,e) -> {
                    if (e != null) {
                        future.completeExceptionally(e);
                    } else {
                        future.complete(r);
                    }
                });
            } catch (Exception e) {
                LOG.error("Fail to show dialog",e);
                ThrowableTool.interruptThreadIfCausedByInterruption(e);
                alertShower.showAlertAndWait(new AlertInfo(e,"error"));
                future.completeExceptionally(e);
            }
        });
        return future;
    }
}
