package perobobbot.timeline;

import lombok.NonNull;
import perococco.perobobbot.timeline.PeroConductor;

public interface Conductor extends PropertyFactory {

    void setTime(double time);
    
    static @NonNull Conductor create() {
        return new PeroConductor();
    }

}
