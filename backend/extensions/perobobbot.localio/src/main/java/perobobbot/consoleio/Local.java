package perobobbot.consoleio;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.common.lang.*;
import perobobbot.common.lang.fp.Function1;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.util.Scanner;

@RequiredArgsConstructor
public class Local implements LocalIO {

    private final @NonNull ApplicationCloser applicationCloser;

    private final @NonNull Listeners<MessageListener> listeners = new Listeners<>();

    private final @NonNull InputReader inputReader = new InputReader();

    private JDialog dialog = null;

    @NonNull
    @Override
    public LocalIO enable() {
        inputReader.start();
        return this;
    }

    @Override
    public void disable() {
        hideGui();
        inputReader.requestStop();
    }

    @Override
    @Synchronized
    public void showGui() {
        if (GraphicsEnvironment.isHeadless() || dialog != null) {
            return;
        }
        System.out.println("SHOULD SHOW GUI");
    }

    @Override
    @Synchronized
    public void hideGui() {
        System.out.println("SHOULD HIDE GUI");
    }

    @Override
    public void print(@NonNull String channel, @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder) {
        final String message = messageBuilder.apply((DispatchContext) Instant::now);
        switch (channel) {
            case CONSOLE: System.out.println(message);break;
            case GUI:
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
            new Thread(this::exitAfterLooperIsDone).start();
        }

        private void exitAfterLooperIsDone() {
            try {
                waitForCompletion();
                applicationCloser.execute();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        @Override
        protected @NonNull IterationCommand performOneIteration() {
            final String line = getNextNotBlankLine();
            if (line.equals("exit")) {
                return IterationCommand.STOP;
            }
            final MessageContext ctx = MessageContext.builder()
                                                     .messageFromMe(false)
                                                     .content(line)
                                                     .rawPayload(line)
                                                     .messageOwner(LOCAL_USER)
                                                     .receptionTime(Instant.now())
                                                     .channelInfo(CONSOLE_CHANNEL_INFO)
                                                     .build();
            listeners.warnListeners(l -> l.onMessage(ctx));
            return IterationCommand.CONTINUE;
        }

        public String getNextNotBlankLine() {
            while (true) {
                final String line = scanner.nextLine();
                if (!line.isBlank()) {
                    return line;
                }
            }
        }
    }

}
