package perobobbot.physics;

import lombok.NonNull;

public enum GravityEffect {
    ATTRACTOR(){
        @Override
        public double applyEffect(@NonNull double gravitationalFactor) {
            return gravitationalFactor;
        }
    },
    REPULSOR() {
        @Override
        public double applyEffect(@NonNull double gravitationalFactor) {
            return -gravitationalFactor;
        }
    },
    NONE() {
        @Override
        public double applyEffect(@NonNull double gravitationalFactor) {
            return 0;
        }
    },
    ;

    public abstract double applyEffect(@NonNull double gravitationalFactor);

}
