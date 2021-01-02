package perobobbot.physics;

public enum GravityEffect {
    ATTRACTOR(){
        @Override
        public double applyEffect(double gravitationalFactor) {
            return gravitationalFactor;
        }
    },
    REPULSOR() {
        @Override
        public double applyEffect(double gravitationalFactor) {
            return -gravitationalFactor;
        }
    },
    NONE() {
        @Override
        public double applyEffect(double gravitationalFactor) {
            return 0;
        }
    },
    ;

    public abstract double applyEffect(double gravitationalFactor);

}
