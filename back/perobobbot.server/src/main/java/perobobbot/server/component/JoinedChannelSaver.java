package perobobbot.server.component;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import perobobbot.data.com.event.ChatChannelJoined;
import perobobbot.data.service.BotService;
import perobobbot.data.service.EventService;
import perobobbot.lang.ApplicationEvent;
import perobobbot.lang.CastTool;
import perobobbot.lang.GatewayChannels;
import perobobbot.lang.ThrowableTool;

import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class JoinedChannelSaver {

    private final @EventService @NonNull BotService botService;

    @ServiceActivator(inputChannel = GatewayChannels.EVENT_MESSAGES)
    public void onEvent(@NonNull ApplicationEvent applicationEvent) {
        CastTool.cast(ChatChannelJoined.class, applicationEvent)
                .map(this::createSaver)
                .map(Thread::new)
                .ifPresent(Thread::start);
    }


    private @NonNull ConnectionSaver createSaver(@NonNull ChatChannelJoined event) {
        return new ConnectionSaver(botService,event.getBotId(), event.getPlatformUserId(), event.getChannelName());
    }

    @RequiredArgsConstructor
    private static class ConnectionSaver implements Runnable {

        private final @NonNull BotService botService;
        private final @NonNull UUID botId;
        private final @NonNull UUID platformUserId;
        private final @NonNull String channelName;

        public void run() {
            try {
                botService.addJoinedChannel(botId, platformUserId, channelName);
            } catch (Exception e) {
                ThrowableTool.interruptThreadIfCausedByInterruption(e);
                LOG.warn("Could not save channel connection information : {}",e.getMessage());
            }
        }
    }

}
