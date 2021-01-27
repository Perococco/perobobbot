package perococco.perobobbot.timeline;

import lombok.NonNull;

public interface ValueHolder {

    double get();

    ValueHolder setTime(double time);

    ValueHolder set(double value);

    ValueHolder clearEasing();

    ValueHolder withEasing(@NonNull Easing easing);
}
