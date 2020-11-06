package perococco.perobobbot.consoleio;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.*;
import perobobbot.common.lang.fp.Function1;
import perobobbot.consoleio.ConsoleIO;

import java.time.Instant;
import java.util.Scanner;
@RequiredArgsConstructor
public class Console implements ConsoleIO {

    private static final String STANDARD = "standard";
    private static final User CONSOLE_USER = new ConsoleUser();
    private static final ChannelInfo CHANNEL_INFO = new ChannelInfo(Platform.CONSOLE, STANDARD);


    private final @NonNull ApplicationCloser applicationCloser;

    private final @NonNull Listeners<MessageListener> listeners = new Listeners<>();

    private final @NonNull InputReader inputReader = new InputReader();

    @NonNull
    @Override
    public ConsoleIO enable() {
        inputReader.start();
        return this;
    }

    @Override
    public void disable() {
        inputReader.requestStop();
    }

    @Override
    public @NonNull Platform getPlatform() {
        return Platform.CONSOLE;
    }

    @Override
    public void print(@NonNull String channel, @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder) {
        if (channel.equals(STANDARD)) {
            final String message = messageBuilder.apply((DispatchContext) Instant::now);
            System.out.println(message);
        }
    }

    @Override
    public @NonNull Subscription addMessageListener(@NonNull MessageListener listener) {
        return listeners.addListener(listener);
    }

    private class InputReader extends Looper {

        private Scanner scanner;

        @Override
        protected void beforeLooping() {
            super.beforeLooping();
            scanner = new Scanner(System.in);
        }

        @Override
        protected void afterLooping() {
            super.afterLooping();
            applicationCloser.execute();
        }

        @Override
        protected @NonNull IterationCommand performOneIteration() throws Exception {
            final String line = scanner.nextLine();
            if (line.equals("stop")) {
                return IterationCommand.STOP;
            }
            final MessageContext ctx = MessageContext.builder()
                                                 .messageFromMe(false)
                                                 .content(line)
                                                 .rawPayload(line)
                                                 .messageOwner(CONSOLE_USER)
                                                 .receptionTime(Instant.now())
                                                 .channelInfo(CHANNEL_INFO)
                                                 .build();
            listeners.warnListeners(l -> l.onMessage(ctx));
            return IterationCommand.CONTINUE;
        }
    }

}
