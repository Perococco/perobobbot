package perobobbot.frontfx.model.dialog;

import lombok.NonNull;
import perobobbot.fx.FXLoader;
import perobobbot.fx.dialog.DialogController;
import perobobbot.lang.fp.Function1;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface DialogHelper {

    <I> void showDialog(
            @NonNull DialogKind dialogKind,
            @NonNull FXLoader loader,
            @NonNull I input);

    @NonNull
    <I,O,C extends DialogController<I,O>> CompletionStage<Optional<O>> showDialog(
            @NonNull DialogKind dialogKind,
            @NonNull Class<C> controllerClass,
            @NonNull Function1<? super Class<C>, ? extends FXLoader> loaderFactory,
            @NonNull I input);

}
