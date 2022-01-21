package perobobbot.discord.gateway.impl.message;


import lombok.NonNull;

public sealed interface SentGatewayEvent permits Identify {

    @NonNull OpCode getOpCode();

}
