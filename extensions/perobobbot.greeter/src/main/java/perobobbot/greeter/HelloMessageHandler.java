package perobobbot.greeter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.MessageContext;
import perobobbot.lang.MessageHandler;

@RequiredArgsConstructor
public class HelloMessageHandler implements MessageHandler {

    @NonNull
    private final HelloIdentity hello;

    @Override
    public void handleMessage(@NonNull MessageContext messageContext) {
        if (!hello.getRootState().hasBeenGreeted(UserOnChannel.from(messageContext))) {
            hello.greetUser(messageContext.getChannelInfo(), messageContext.getMessageOwner());
        }
    }
    
}
