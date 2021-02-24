package perobobbot.server.config.extension;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import perobobbot.lang.fp.Either;
import perobobbot.plugin.PluginUsingServices;
import perobobbot.plugin.Requirement;
import perobobbot.plugin.ServiceProvider;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
public class SimpleServiceProviderFactory implements ServiceProviderFactory {


    private static final Collector<SearchResult, ?, ImmutableMap<Class<?>, Object>> SEARCH_RESULT_TO_IMMUTABLE_MAP =
            ImmutableMap.toImmutableMap(SearchResult::getServiceType, r -> r.getService()
                                                                            .orElseThrow(() -> new RuntimeException(
                                                                                    "BUG in search result filtering")));

    private final @NonNull List<Object> pluginServices;


    @Override
    public void addService(@NonNull Object service) {
        pluginServices.add(service);
    }

    public @NonNull Optional<ServiceProvider> createServiceProvider(@NonNull PluginUsingServices pluginUsingServices) {
        final var executionResult = new Execution(pluginUsingServices.getRequirements()).execute();


        executionResult.left().ifPresent(
                l -> LOG.warn("Could not find all services for plugin {} : {}", pluginUsingServices.getName(), l));
        return executionResult.right();
    }


    @RequiredArgsConstructor
    private class Execution {

        private final @NonNull ImmutableSet<Requirement> requirements;

        private List<SearchResult> searchResults;

        private List<Class<?>> requiresServicesNotFound;

        private ServiceProvider serviceProvider;

        private Either<ImmutableSet<Class<?>>, ServiceProvider> execute() {
            this.searchRequiredServices();
            this.collectFailedSearchResults();
            if (this.searchSucceeded()) {
                this.createServiceProvider();
                return Either.right(serviceProvider);
            } else {
                return Either.left(ImmutableSet.copyOf(requiresServicesNotFound));
            }
        }

        private void searchRequiredServices() {
            this.searchResults = requirements.stream().map(this::findService).collect(Collectors.toList());
        }

        private @NonNull SearchResult findService(Requirement requirement) {
            return pluginServices.stream()
                                 .filter(requirement::fullFillRequirement)
                                 .findFirst()
                                 .map(s -> new SearchResult(requirement.getServiceType(), s, true))
                                 .orElseGet(() -> new SearchResult(requirement.getServiceType(), null,
                                                                   requirement.isOptional()));
        }


        private void collectFailedSearchResults() {
            this.requiresServicesNotFound = this.searchResults.stream()
                                                              .filter(r -> !r.isSearchSuccessful())
                                                              .map(SearchResult::getServiceType)
                                                              .collect(Collectors.toList());
        }

        private boolean searchSucceeded() {
            return this.requiresServicesNotFound.isEmpty();
        }

        private void createServiceProvider() {
            this.serviceProvider = this.searchResults.stream()
                                                     .filter(SearchResult::hasService)
                                                     .collect(Collectors.collectingAndThen(
                                                             SEARCH_RESULT_TO_IMMUTABLE_MAP,
                                                             PluginSpecificServiceProvider::new));
        }


    }

    @Value
    private static class SearchResult {

        @NonNull Class<?> serviceType;
        @Getter(AccessLevel.NONE)
        Object service;
        boolean searchSuccessful;

        public @NonNull Optional<Object> getService() {
            return Optional.ofNullable(service);
        }

        public boolean hasService() {
            return service != null;
        }
    }
}
