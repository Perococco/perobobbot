package perobobbot.physics;

import lombok.NonNull;

public interface AccelerationsModifier {

    AccelerationsModifier NOP = f -> {};

    void modifyAccelerations(@NonNull Accelerations accelerations);
}
