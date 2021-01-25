package perobobbot.rest.client;

import lombok.NonNull;


public interface ClientManagerFactory {

    @NonNull ClientManager create(@NonNull String baseUrl);
}
