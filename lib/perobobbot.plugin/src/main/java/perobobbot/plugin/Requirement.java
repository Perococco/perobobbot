package perobobbot.plugin;

import lombok.NonNull;
import lombok.Value;

@Value
public class Requirement {

    public static @NonNull Requirement optional(@NonNull Class<?> serviceType) {
        return new Requirement(serviceType,true);
    }

    public static @NonNull Requirement required(@NonNull Class<?> serviceType) {
        return new Requirement(serviceType,false);
    }


    @NonNull Class<?> serviceType;
    boolean optional;

    public boolean fullFillRequirement(Object o) {
        return serviceType.isInstance(o);
    }
}
