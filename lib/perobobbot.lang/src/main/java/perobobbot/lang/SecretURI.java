package perobobbot.lang;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.net.URI;

@RequiredArgsConstructor
public class SecretURI {

    @Getter
    private final @NonNull URI uri;

    @Override
    public String toString() {
        return "SecretURI{" + uri.getHost() + "/**********}";
    }
}
