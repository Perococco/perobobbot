package perococco.perobobbot.timeline;

import lombok.NonNull;
import perobobbot.timeline.Conductor;
import perobobbot.timeline.Property;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class PeroConductor implements Conductor {

    private List<Reference<PeroProperty>> properties = new LinkedList<>();

    private double time;

    @Override
    public void setTime(double time) {
        this.time = time;
        var itr = properties.iterator();
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
        final PeroProperty property = new PeroProperty();
        property.setTime(this.time);
        properties.add(new WeakReference<>(property));
        return property;
    }
}
