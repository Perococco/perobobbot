package perobobbot.action;

import javafx.beans.property.BooleanProperty;
import lombok.NonNull;

/**
 * Binding between an action and an FX Node.
 */
public interface ActionBinding {

    @NonNull
    BooleanProperty filteredProperty();

    void bind();

    void unbind();

    default void setFiltered(boolean filtered) {
        filteredProperty().setValue(filtered);
    }

    default boolean isFiltered() {
        return filteredProperty().get();
    }


}
