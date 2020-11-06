package perococco.perobobbot.services;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.services.Services;
import perobobbot.services.ServicesBuilder;

import java.util.HashSet;
import java.util.Set;

public class PerococcoServicesBuilder implements ServicesBuilder {

    @NonNull
    private final Set<Object> services = new HashSet<>();

    @Override
    public @NonNull ServicesBuilder addService(@NonNull Object service) {
        services.add(service);
        return this;
    }

    @Override
    public @NonNull Services build() {
        return new PerococcoServices(ImmutableSet.copyOf(services));
    }


}
