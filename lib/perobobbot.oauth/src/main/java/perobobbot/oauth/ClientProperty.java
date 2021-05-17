package perobobbot.oauth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Secret;

@Value
public class ClientProperty {

    @NonNull String id;
    @NonNull Secret secret;

    @JsonIgnore
    public @NonNull String getSecretValue() {
        return secret.getValue();
    }
}
