package perococco.perobobbot.common.lang;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class SplitterAccumulator<T> {

    private final int groupSize;

    private final List<T> building = new ArrayList<>();

    private final ImmutableList.Builder<ImmutableList<T>> builder = ImmutableList.builder();

    public void addElement(@NonNull T t) {
        if (building.size()<groupSize) {
            building.add(t);
        } else {
            builder.add(ImmutableList.copyOf(building));
            building.clear();
        }
    }

    public SplitterAccumulator combine(SplitterAccumulator<T> other) {
        builder.addAll(other.builder.build());
        other.building.forEach(this::addElement);
        return this;
    }

    public ImmutableList<ImmutableList<T>> build() {
        if (!building.isEmpty()) {
            builder.add(ImmutableList.copyOf(building));
        }
        return builder.build();
    }
}
