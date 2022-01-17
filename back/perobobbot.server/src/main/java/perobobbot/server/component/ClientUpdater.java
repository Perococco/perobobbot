package perobobbot.server.component;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import perobobbot.data.com.CreateClientParameter;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.EventService;
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

    private final @NonNull
    @EventService
    ClientService clientService;

    @Value("${client-info.path}")
    @Getter
    private final @NonNull Path clientInfoPath;

    public void update() throws IOException {
        final var updaters = parseUpdater();
        updaters.forEach(u -> u.update(clientService));
    }

    private ImmutableList<Updater> parseUpdater() throws IOException {
        final var lines = Files.readAllLines(clientInfoPath);
        final var builder = ImmutableList.<Updater>builder();
        for (int i = 0; i < lines.size(); i++) {
            final var lineNumber = i;
            Updater.parse(lines.get(lineNumber))
                   .ifPresentOrElse(builder::add, () -> warnLineCannotBeParsed(lineNumber));
        }

        return builder.build();
    }

    private void warnLineCannotBeParsed(int lineNumber) {
        LOG.warn("Could not parse client info on line {} in file {}", lineNumber+1, clientInfoPath);
    }

    public sealed interface Updater permits TwitchClientUpdater, DiscordClientUpdater {
        void update(@NonNull ClientService clientService);

        static @NonNull Optional<Updater> parse(@NonNull String line) {
            final var tokens = line.split(" ");
            if (tokens.length < 3) {
                return Optional.empty();
            }

            final var platform = IdentifiedEnumTools.findEnum(tokens[0], Platform.class).orElse(null);
            final var clientId = tokens[1];
            final var clientSecret = Secret.with(tokens[2]);

            if (platform == null) {
                return Optional.empty();
            }

            final var parameter = new CreateClientParameter(platform, clientId, clientSecret);

            final var updater = switch (platform) {
                case DISCORD -> new DiscordClientUpdater(parameter, tokens.length > 3 ? Secret.with(tokens[3]) : null);
                case TWITCH -> new TwitchClientUpdater(parameter);
                default -> null;
            };

            return Optional.ofNullable(updater);
        }

    }


    public record TwitchClientUpdater(@NonNull CreateClientParameter parameter) implements Updater {

        @Override
        public void update(@NonNull ClientService clientService) {
            assert parameter.getPlatform() == Platform.TWITCH;
            clientService.createClient(parameter);
        }
    }

    public record DiscordClientUpdater(@NonNull CreateClientParameter parameter, Secret botToken) implements Updater {

        @Override
        public void update(@NonNull ClientService clientService) {
            assert parameter.getPlatform() == Platform.DISCORD;
            clientService.createClient(parameter);
            if (botToken != null) {
                clientService.setDiscordBotToken(botToken);
            }
        }
    }

}
