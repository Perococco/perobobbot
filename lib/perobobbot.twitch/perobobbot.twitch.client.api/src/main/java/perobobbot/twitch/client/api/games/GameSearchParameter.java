package perobobbot.twitch.client.api.games;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.client.api.QueryParameterBuilder;
import perobobbot.twitch.client.api.QueryParametersProvider;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Value
public class GameSearchParameter implements QueryParametersProvider {

    @NonNull String[] ids;
    @NonNull String[] names;

    @Override
    public @NonNull Map<String, Collection<?>> createQueryParameters() {
        return QueryParameterBuilder.builder()
                                    .setValues("id", ids)
                                    .setValues("name", names)
                                    .build();
    }
}
