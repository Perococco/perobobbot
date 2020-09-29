package perobobbot.server.config;

import lombok.NonNull;
import org.springframework.integration.annotation.ServiceActivator;
import perobobbot.common.lang.MessageContext;
import perobobbot.program.core.ProgramExecutor;
import perobobbot.program.core.ProxyProgramExecutor;

public class MsgProgramExecutor extends ProxyProgramExecutor {

    public MsgProgramExecutor(@NonNull ProgramExecutor programExecutor) {
        super(programExecutor);
    }

    @ServiceActivator(inputChannel = "chatChannel")
    @Override
    public void handleMessage(@NonNull MessageContext messageContext) {
        super.handleMessage(messageContext);
    }

    @Override
    public void stop() {
        super.stop();
    }
}
