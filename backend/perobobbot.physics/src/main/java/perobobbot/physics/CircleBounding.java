package perobobbot.physics;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class CircleBounding implements BoundingBox2D {

    private final @NonNull ROEntity2D entity;

    @Getter @Setter
    private double size;

    public CircleBounding(@NonNull ROEntity2D entity, double size) {
        this.entity = entity;
        this.size = size;
    }

    @Override
    public boolean isInside(@NonNull ROVector2D position) {
        return position.normOfDifference(entity.getPosition()) <= size;
    }


}
