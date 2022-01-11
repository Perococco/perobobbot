package perobobbot.lang;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.UUID;
import java.util.stream.Collectors;

@Value
@Builder
@TypeScript
public class UserIdentification {
    @NonNull UUID id;

    @Singular
    @NonNull ImmutableMap<Platform, PlatformUser> platformUsers;


    public @NonNull String prettyString() {
        return platformUsers.values()
                            .stream()
                            .map(PlatformUser::prettyPrint)
                            .collect(Collectors.joining(", "));
    }
}
