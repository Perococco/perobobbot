package perobobbot.physics;

import lombok.NonNull;
import perococco.perobobbot.physics.PeroUniverse;

import java.util.*;

public interface Universe {

    static @NonNull Universe create() {
        return new PeroUniverse();
    }

    void addEntity(@NonNull Entity2D entity2D);

    void removeEntity(@NonNull UUID entityId);

    void update(double dt);

}
