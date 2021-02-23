package perobobbot.server.config.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.chat.core.DisposableIO;
import perobobbot.chat.core.IO;
import perobobbot.chat.core.IOBuilder;
import perobobbot.lang.ThrowableTool;
import perobobbot.plugin.PlatformChatPlugin;
import perobobbot.plugin.PluginList;
import perobobbot.server.component.MessageGateway;

import java.util.Optional;

@RequiredArgsConstructor
@Configuration
@Log4j2
public class IOConfiguration {

    private final @NonNull PlatformChatPlugin.Parameters parameters;

    private final @NonNull PluginList pluginList;

    @NonNull
    private final MessageGateway messageGateway;

    @Bean(destroyMethod = "dispose")
    public DisposableIO io() {

        final IOBuilder builder = IO.builder();

        pluginList.streamPlugins(PlatformChatPlugin.class)
                  .map(this::createChatPlatform)
                  .flatMap(Optional::stream)
                  .peek(p -> System.out.println(p.getPlatform()))
                  .forEach(pio -> {
                      pio.addMessageListener(messageGateway::sendPlatformMessage);
                      builder.add(pio.getPlatform(), pio);
                  });

        return builder.build();
    }

    private @NonNull Optional<ChatPlatform> createChatPlatform(@NonNull PlatformChatPlugin plugin) {
        try {
            return Optional.of(plugin.create(parameters));
        } catch (Exception e) {
            ThrowableTool.interruptThreadIfCausedByInterruption(e);
            LOG.warn("Could create Chat Platform {} : {}", plugin.getName(), e.getMessage());
            LOG.debug(e);
            return Optional.empty();
        }
    }

}
