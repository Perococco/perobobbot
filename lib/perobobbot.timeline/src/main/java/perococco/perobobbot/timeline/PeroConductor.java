package perococco.perobobbot.timeline;

import lombok.NonNull;
import perobobbot.lang.fp.Function0;
import perobobbot.timeline.Conductor;
import perobobbot.timeline.Property;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class PeroConductor implements Conductor {

    private List<Reference<TimedItem>> items = new LinkedList<>();

    private double time;

    @Override
    public void setTime(double time) {
        this.time = time;
        var itr = items.iterator();
        while(itr.hasNext()) {
            var prop = itr.next().get();
            if (prop == null) {
                itr.remove();
            } else {
                prop.setTime(time);
            }
        }
    }

    @Override
    public @NonNull Property createProperty() {
        return addTimedItem(PeroProperty::new);
    }

    private @NonNull <T extends TimedItem> T addTimedItem(@NonNull Function0<T> itemSupplier) {
        final T item = itemSupplier.get();
        item.setTime(time);
        items.add(new WeakReference<>(item));
        return item;
    }
}
