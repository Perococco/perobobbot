package perobobbot.twitch.client.api;

import lombok.NonNull;

import java.util.Collection;
import java.util.Map;

public interface QueryParametersProvider {

    @NonNull Map<String, Collection<?>> createQueryParameters();

}
