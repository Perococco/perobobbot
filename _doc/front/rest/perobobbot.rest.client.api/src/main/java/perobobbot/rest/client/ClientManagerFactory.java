package perobobbot.rest.client;

import lombok.NonNull;

import java.util.ServiceLoader;


public interface ClientManagerFactory {

    @NonNull ClientManager create(@NonNull String baseUrl);

    static @NonNull ClientManagerFactory getInstance() {
        return ServiceLoader.load(ClientManagerFactory.class)
                            .stream()
                            .map(ServiceLoader.Provider::get)
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException(
                                    "Could not found any implementation of ClientManagerFactory"));
    }
}
