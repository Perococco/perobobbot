package perococco.perobobbot.frontfx.action;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.NonNull;
import perobobbot.action.ActionBinding;

public class NopActionBinding implements ActionBinding {

    private final BooleanProperty booleanProperty = new SimpleBooleanProperty();

    @Override
    public @NonNull BooleanProperty filteredProperty() {
        return booleanProperty;
    }

    @Override
    public void bind() {}

    @Override
    public void unbind() {}
}
