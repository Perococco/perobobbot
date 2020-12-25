package perobobbot.physics;

import lombok.NonNull;

public interface BoundingBox2D {

    BoundingBox2D NONE = p -> false;

    boolean isInside(@NonNull ROVector2D position);
}
