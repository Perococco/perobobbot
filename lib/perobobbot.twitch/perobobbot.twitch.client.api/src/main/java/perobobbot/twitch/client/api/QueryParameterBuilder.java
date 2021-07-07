package perobobbot.twitch.client.api;

import lombok.NonNull;

import java.util.*;

public class QueryParameterBuilder {

    private final @NonNull Map<String,Collection<?>> queryParameter = new HashMap<>();

    public static @NonNull QueryParameterBuilder builder() {
        return new QueryParameterBuilder();
    }

    public @NonNull Map<String, Collection<?>> build() {
        return queryParameter;
    }

    public QueryParameterBuilder setValue(String key, Object value) {
        if (value != null) {
            setValues(key, Collections.singletonList(value));
        }
        return this;
    }

    public QueryParameterBuilder setValues(String key, Object[] values) {
        if (values != null) {
            setValues(key,Arrays.asList(values));
        }
        return this;
    }

    public QueryParameterBuilder setValues(String key, Collection<?> values) {
        if (values != null && !values.isEmpty()) {
            this.queryParameter.put(key,values);
        }
        return this;
    }
}
