package perobobbot.greeter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.MessageContext;
import perobobbot.lang.MessageHandler;
import perobobbot.lang.fp.Value3;

@RequiredArgsConstructor
public class HelloMessageHandler implements MessageHandler {

    @NonNull
    private final HelloIdentity hello;

    @Override
    public void handleMessage(@NonNull MessageContext messageContext) {
        final var value = Value3.of(
                messageContext.getChatConnectionInfo(),
                messageContext.getMessageOwner(),
                messageContext.getChannelInfo()
        );

        hello.getRootState()
             .thenAcceptAsync(s -> {
                 if (!s.hasBeenGreeted(value)) {
                     hello.greetUser(value);
                 }
             });
    }

}
