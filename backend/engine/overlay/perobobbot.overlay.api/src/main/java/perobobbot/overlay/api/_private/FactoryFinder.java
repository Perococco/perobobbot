package perobobbot.overlay.api._private;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.overlay.api.OverlayController;

import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

@Log4j2
public class FactoryFinder {

    public Optional<OverlayController.Factory> find() {
        final List<OverlayController.Factory> factories = ServiceLoader.load(OverlayController.Factory.class)
                                                                       .stream()
                                                                       .map(ServiceLoader.Provider::get)
                                                                       .collect(Collectors.toList());

        if (factories.size() > 1) {
            LOG.warn("Multiple implementations of the overlay : {}", factories.stream()
                                                                              .map(OverlayController.Factory::getImplementationName)
                                                                              .collect(Collectors.joining(", ")));
        }

        final Optional<OverlayController.Factory> factory = switch (factories.size()) {
            case 0 -> Optional.empty();
            case 1 -> Optional.ofNullable(factories.get(0));
            default -> filterOutDefaultImplementation(factories);
        };

        factory.filter(OverlayController.Factory::isDefault).ifPresent(f -> LOG.info("Use '{}' implementation for Overlay", f.getImplementationName()));

        return factory;
    }

    private Optional<OverlayController.Factory> filterOutDefaultImplementation(@NonNull List<OverlayController.Factory> factories) {
        return factories.stream()
                        .filter(f -> !f.isDefault())
                        .findAny();
    }
}
