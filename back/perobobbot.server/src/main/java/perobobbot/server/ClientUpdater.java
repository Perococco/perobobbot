package perobobbot.server;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import perobobbot.data.com.CreateClientParameter;
import perobobbot.data.service.ClientService;
import perobobbot.lang.IdentifiedEnum;
import perobobbot.lang.IdentifiedEnumTools;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
public class ClientUpdater {

    private final @NonNull ClientService clientService;
    private final @NonNull Path clientInfoPath;

    public void update() throws IOException {
        final var parameters = parseClientInfo();

        parameters.forEach(clientService::createClient);
    }

    private ImmutableList<CreateClientParameter> parseClientInfo() throws IOException {
        final var lines = Files.readAllLines(clientInfoPath);
        final var builder = ImmutableList.<CreateClientParameter>builder();
        for (int i = 0; i < lines.size(); i++) {
            final var lineNumber = i;
            parseOneLineOfClientInfo(lines.get(i))
                    .ifPresentOrElse(builder::add, () -> warnLineCannotBeParsed(lineNumber));
        }

        return builder.build();
    }

    private @NonNull Optional<CreateClientParameter> parseOneLineOfClientInfo(@NonNull String line) {
        final var token = line.split(" ");
        if (token.length<3) {
            return Optional.empty();
        }
        return IdentifiedEnumTools.findEnum(token[0], Platform.class)
                                  .map(p -> new CreateClientParameter(p, token[1], Secret.with(token[2])));

    }

    private void warnLineCannotBeParsed(int lineNumber) {
        LOG.warn("Could not parse client info on line {} in file {}",lineNumber,clientInfoPath);
    }


}
