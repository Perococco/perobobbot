package perobobbot.server.component;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import perobobbot.data.com.CreateClientParameter;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.EventService;
import perobobbot.lang.IdentifiedEnum;
import perobobbot.lang.IdentifiedEnumTools;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
public class ClientUpdater {

    private final @NonNull @EventService
    ClientService clientService;

    @Value("${client-info.path}")
    @Getter
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
            CreateClientParameter.parse(lines.get(lineNumber))
                    .ifPresentOrElse(builder::add, () -> warnLineCannotBeParsed(lineNumber));
        }

        return builder.build();
    }

    private void warnLineCannotBeParsed(int lineNumber) {
        LOG.warn("Could not parse client info on line {} in file {}",lineNumber,clientInfoPath);
    }


}
