package perobobbot.physics;

import lombok.NonNull;

import java.util.UUID;

public interface EntityListener {

    void onGravitationEffect(@NonNull UUID entityId, @NonNull GravityEffect oldEffect, @NonNull GravityEffect newEffect);

    void onForceModifier(@NonNull UUID entityId, @NonNull AccelerationsModifier oldModifier, @NonNull AccelerationsModifier newModifier);
}
