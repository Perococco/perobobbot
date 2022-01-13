package perobobbot.data.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.IdentifiedEnumTools;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;
import perobobbot.lang.TypeScript;

import java.util.Optional;

@Value
@TypeScript
public class CreateClientParameter {
    @NonNull Platform platform;
    @NonNull String clientId;
    @NonNull Secret clientSecret;


    /**
     * @param line a 3 word line. First, second and third word are platform identification, client id and client secret respectively.
     * @return an optional containing the parse parameters if the line could be parsed, an empty optional otherwise
     */
    public static @NonNull Optional<CreateClientParameter> parse(@NonNull String line) {
        final var token = line.split(" ");
        if (token.length<3) {
            return Optional.empty();
        }

        final var platform = IdentifiedEnumTools.findEnum(token[0],Platform.class).orElse(null);
        final var clientId = token[1];
        final var clientSecret = Secret.with(token[2]);

        if (platform == null) {
            return Optional.empty();
        }

        return Optional.of(new CreateClientParameter(platform, clientId, clientSecret));
    }


}
