package perobobbot.lang;

import lombok.*;

import java.util.UUID;

@Value
@Builder
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public class Client {
    @NonNull UUID id;
    @NonNull Platform platform;
    @NonNull String clientId;
    @NonNull Secret clientSecret;

    public @NonNull SafeClient stripSecret() {
        return new SafeClient(id,platform,clientId);
    }
}
