package perococco.perobobbot.frontfx.model;

import com.google.common.collect.ImmutableSet;
import lombok.*;
import perobobbot.frontfx.model.Selection;
import perobobbot.state.SetState;

import java.util.Optional;

@EqualsAndHashCode
@RequiredArgsConstructor
public class PeroSelection<T> implements Selection<T> {

    public static <T> PeroSelection<T> empty() {
        return new PeroSelection<>();
    }

    public static <T> PeroSelection<T> with(@NonNull T mainSelection, @NonNull ImmutableSet<T> selectedElements) {
        assert selectedElements.contains(mainSelection) : "Main selection is not in the selected elements";
        return new PeroSelection<>(mainSelection, new SetState<>(selectedElements));
    }

    /**
     * The main selection. If several elements are selected, this is the one that
     * will be used for single element action. It is also the anchor when performing multi selection
     * operation
     */
    @Getter(AccessLevel.NONE)
    private final T mainSelection;

    /**
     * All the selected elements, including the main selection.
     */
    @NonNull
    private final SetState<T> selectedElements;


    private PeroSelection() {
        this.selectedElements = SetState.empty();
        this.mainSelection = null;
    }


    @Override
    public Optional<T> getMainSelection() {
        return Optional.ofNullable(mainSelection);
    }

    @Override
    public @NonNull Selection<T> removeFromSelection(@NonNull T item) {
        if (!selectedElements.contains(item)) {
            return this;
        }
        final SetState<T> newSelectedElements = selectedElements.remove(item);
        if (mainSelection.equals(item)) {
            return newSelectedElements.stream()
                                      .findFirst()
                                      .map(ms -> new PeroSelection<>(ms, newSelectedElements))
                                      .orElseGet(PeroSelection::empty);
        } else {
            return new PeroSelection<>(mainSelection, newSelectedElements);
        }
    }

    @Override
    public @NonNull ImmutableSet<T> getSelectedElements() {
        return selectedElements.getContent();
    }

    @Override
    public boolean isSelected(@NonNull T item) {
        return selectedElements.contains(item);
    }
}

