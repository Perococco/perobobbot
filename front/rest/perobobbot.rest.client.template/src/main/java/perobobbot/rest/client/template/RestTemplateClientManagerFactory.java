package perobobbot.rest.client.template;

import lombok.NonNull;
import perobobbot.rest.client.ClientManager;
import perobobbot.rest.client.ClientManagerFactory;
import perococco.perobobbot.rest.client.template.PeroRestTemplateClientManager;

public class RestTemplateClientManagerFactory implements ClientManagerFactory {

    @Override
    public @NonNull ClientManager create(@NonNull String baseUrl) {
        return new PeroRestTemplateClientManager(baseUrl);
    }
}