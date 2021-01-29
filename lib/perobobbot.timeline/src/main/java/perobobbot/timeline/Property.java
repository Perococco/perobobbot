package perobobbot.timeline;

import lombok.NonNull;

import java.time.Duration;

public interface Property extends ReadOnlyProperty {

    /**
     * Clear the easing and return this
     * @return this
     */
    Property clearEasing();

    /**
     * Set the easing and return this
     * @param easingType the type of easing
     * @param easingDuration the duration of the easing
     * @return this
     */
    Property setEasing(@NonNull EasingType easingType, @NonNull Duration easingDuration);

    /**
     * @param value the value to set
     * @return this
     */
    Property set(double value);

    Property forceSet(double value);

    double get();

    double getTarget();
}
