package perobobbot.fx;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.CastTool;

import java.util.Optional;

@RequiredArgsConstructor
public class TypedFXLoadingResult<C> {

    @NonNull
    private final C controller;

    @NonNull
    private final Object root;

    @NonNull
    public <T> Optional<T> getRoot(@NonNull Class<T> rootType) {
        return CastTool.cast(rootType, root);
    }


    @SuppressWarnings("unchecked")
    @NonNull
    public <T> T getRoot() {
        return (T) root;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public C getController() {
        return controller;
    }

}
