package perobobbot.frontfx.model.view;

import lombok.NonNull;

import java.util.Optional;

public interface FXViewProvider {

    @NonNull
    <C extends FXView> Optional<FXView> findFXView(@NonNull Class<? extends C> fxViewType);

    @NonNull
    default <C extends FXView> FXView getFXView(@NonNull Class<? extends C> fxViewType) {
        return findFXView(fxViewType).orElseGet(EmptyFXView::create);
    }

    @NonNull
    default FXView getEmptyView() {
        return EmptyFXView.create();
    }

}
