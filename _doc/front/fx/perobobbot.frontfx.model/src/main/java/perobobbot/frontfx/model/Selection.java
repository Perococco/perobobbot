package perobobbot.frontfx.model;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perococco.perobobbot.frontfx.model.PeroSelection;

import java.util.Optional;

/**
 * @author perococco
 */
public interface Selection<T> {

    static <T>Selection<T> empty() {
        return PeroSelection.empty();
    }

    static <T> Selection<T> with(@NonNull T mainSelection, @NonNull ImmutableSet<T> selectedElements) {
        return PeroSelection.with(mainSelection, selectedElements);
    }

    /**
     * The main selection. If several elements are selected, this is the one that
     * will be used for single element action. It is also the anchor when performing multi selection
     * operation
     */
    Optional<T> getMainSelection();

    /**
     * All the selected elements, including the main selection.
     */
    @NonNull
    ImmutableSet<T> getSelectedElements();

    @NonNull
    Selection<T> removeFromSelection(@NonNull T item);

    boolean isSelected(@NonNull T item);

}
