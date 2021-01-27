package perobobbot.timeline;

import lombok.NonNull;

import java.time.Duration;

public interface Property {

    void clearEasing();

    void setEasing(@NonNull EasingType easing, @NonNull Duration easingDuration);

    void set(double value);

    double get();
}
