package perobobbot.service.core;

import lombok.NonNull;

public interface ServicesBuilder {

    int DEFAULT_PRIORITY = -100;

    @NonNull
    <T> ServicesBuilder addService(@NonNull Class<T> type, @NonNull T instance, int priority);

    @NonNull
    ServicesBuilder addService(@NonNull Object instance, int priority);

    @NonNull
    default <T> ServicesBuilder addService(@NonNull Class<T> type, @NonNull T instance) {
        return addService(type,instance,DEFAULT_PRIORITY);
    }

    @NonNull
    default ServicesBuilder addService(@NonNull Object instance) {
        return addService(instance,DEFAULT_PRIORITY);
    }

    @NonNull
    Services build();

}
