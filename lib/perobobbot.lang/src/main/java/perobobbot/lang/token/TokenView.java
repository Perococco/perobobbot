package perobobbot.lang.token;

import lombok.NonNull;

import java.util.UUID;

public interface TokenView<T> {

    @NonNull UUID getId();

    @NonNull Token<T> getToken();

}
