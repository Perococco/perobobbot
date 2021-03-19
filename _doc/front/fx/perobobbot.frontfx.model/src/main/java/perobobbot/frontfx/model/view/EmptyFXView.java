package perobobbot.frontfx.model.view;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EmptyFXView implements FXView {

    public static EmptyFXView create() {
        return INSTANCE;
    }

    private static final EmptyFXView INSTANCE = new EmptyFXView();

    @Override
    public @NonNull FXViewInstance getViewInstance() {
        return FXViewInstance.EMPTY;
    }

}
