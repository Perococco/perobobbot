package perobobbot.lang;

import lombok.NonNull;

import java.net.URI;

public interface URIResolver {


    @NonNull URI resolve(@NonNull String path);

    default @NonNull String toURI() {
        return resolve("").toString();
    }

    default @NonNull URIResolver addPrefix(@NonNull String prefix) {
        return path -> resolve(prefix+path);
    }

}
