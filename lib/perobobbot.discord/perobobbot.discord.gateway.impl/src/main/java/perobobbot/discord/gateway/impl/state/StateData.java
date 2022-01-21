package perobobbot.discord.gateway.impl.state;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.discord.gateway.impl.MessageMapper;
import perobobbot.discord.resources.GatewayBot;
import perobobbot.lang.Secret;
import perobobot.discord.gateway.api.GatewayEventListener;

@RequiredArgsConstructor
@Getter
@Builder
public class StateData {
    private final @NonNull GatewayBot gatewayBot;
    private final int version;
    private final @NonNull MessageMapper messageMapper;
    private final @NonNull Secret botToken;
    private final @NonNull GatewayEventListener listener;

}
