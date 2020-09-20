package perococco.perobobbot.service.core;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.service.core.Services;
import perobobbot.service.core.ServicesBuilder;

import java.util.HashMap;
import java.util.Map;

public class PerococcoServicesBuilder implements ServicesBuilder {

    @NonNull
    private final Map<Class<?>,ServiceHolder<?>> services = new HashMap<>();

    @Override
    public @NonNull <T> ServicesBuilder addService(@NonNull Class<T> type, @NonNull T instance, int priority) {
        return notGenericAddService(type,instance,priority);
    }

    @Override
    public @NonNull ServicesBuilder addService(@NonNull Object instance, int priority) {
        return notGenericAddService(instance.getClass(),instance,priority);
    }

    private ServicesBuilder notGenericAddService(@NonNull Class<?> type, @NonNull Object instance, int priority) {
        if (!type.isInstance(instance)) {
            throw new IllegalArgumentException("Bug in PerobobbotServicesBuilder");
        }
        final ServiceHolder<?> serviceHolder = services.get(type);
        if (serviceHolder == null || serviceHolder.getPriority()<priority) {
            this.services.put(type,new ServiceHolder<>(instance,priority));
        }
        return this;
    }

    @Override
    public @NonNull Services build() {
        final ImmutableMap<Class<?>,Object> services = this.services.entrySet()
                                                                    .stream()
                                                                    .collect(ImmutableMap.toImmutableMap(
                                                                            Map.Entry::getKey,
                                                                            e -> e.getValue().getService()
                                                                    ));
        return new PerococcoServices(services);
    }


}
