package perococco.perobobbot.services;

import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.services.Services;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ServicesFilter {

    @NonNull
    public static Services filter(@NonNull Services services, @NonNull ImmutableSet<Class<?>> requiredServices, @NonNull ImmutableSet<Class<?>> optionalServices) {
        return new ServicesFilter(services, requiredServices, optionalServices).filter();
    }

    @NonNull
    private final Services services;

    @NonNull
    private final ImmutableSet<Class<?>> requiredServices;

    @NonNull
    private final ImmutableSet<Class<?>> optionalServices;

    @NonNull
    private final ImmutableSet.Builder<Object> filteredServices = ImmutableSet.builder();

    @NonNull
    private Services filter() {
        this.retrieveAllRequiredServices();
        this.retrieveOptionalServices();

        return Services.create(filteredServices.build());
    }

    private void retrieveAllRequiredServices() {
        requiredServices.stream()
                        .map(services::getService)
                        .forEach(filteredServices::add);
    }

    private void retrieveOptionalServices() {
        optionalServices.stream().map(services::findService)
                        .flatMap(Optional::stream)
                        .forEach(filteredServices::add);
    }


}
