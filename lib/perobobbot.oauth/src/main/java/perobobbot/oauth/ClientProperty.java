package perobobbot.oauth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import perobobbot.lang.Platform;
import perobobbot.lang.Scope;
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
