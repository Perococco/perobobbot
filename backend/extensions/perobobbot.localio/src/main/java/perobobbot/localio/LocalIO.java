package perobobbot.localio;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.chat.core.DispatchSlip;
import perobobbot.chat.core.MessageChannelIO;
import perobobbot.command.CommandBundleLifeCycle;
import perobobbot.lang.*;
import perobobbot.lang.fp.Function1;
import perobobbot.localio.swing.InputPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class LocalIO implements LocalChat {

    public static final String QUIT_COMMAND = "quit";

    private final @NonNull ApplicationCloser applicationCloser;

    private final @NonNull CommandBundleLifeCycle commandBundleLifeCycle;

    private final @NonNull Listeners<MessageListener> listeners;

    private final @NonNull InputReader inputReader = new InputReader();

    private JFrame dialog = null;
    private SubscriptionHolder guiSubscription = new SubscriptionHolder();

    public LocalIO(@NonNull ApplicationCloser applicationCloser,
                   @NonNull Function1<LocalIO, CommandBundleLifeCycle> commandBundleLifeCycleFactory,
                   @NonNull Listeners<MessageListener> listeners) {
        this.applicationCloser = applicationCloser;
        this.commandBundleLifeCycle = commandBundleLifeCycleFactory.f(this);
        this.listeners = listeners;
    }

    @Override
    public @NonNull CompletionStage<? extends DispatchSlip> send(@NonNull Function1<? super DispatchContext, ? extends String> messageBuilder) {
        final String message = messageBuilder.apply((DispatchContext) Instant::now);
        System.out.println(message);
        return CompletableFuture.completedFuture(DispatchSlip.with(this,Instant.now()));
    }

    @NonNull
    @Override
    public LocalChat enable() {
        showGui();
        commandBundleLifeCycle.attachCommandBundle();
        inputReader.start();
        return this;
    }

    @Override
    public void disable() {
        hideGui();
        inputReader.requestStop();
        commandBundleLifeCycle.detachCommandBundle();
    }

    @Override
    @Synchronized
    public void showGui() {
        if (GraphicsEnvironment.isHeadless() || dialog != null) {
            return;
        }
        final InputPanel inputPanel = new InputPanel();
        this.guiSubscription.replaceWith(() -> inputPanel.addListener(this::onGuiMessage));
        this.dialog = new JFrame("Local Command");
        this.dialog.getContentPane().add(inputPanel);
        this.dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                guiSubscription.unsubscribe();
                dialog = null;
            }
        });
        this.dialog.pack();
        this.dialog.setVisible(true);
        this.dialog.setAlwaysOnTop(true);
        this.dialog.toFront();
    }

    @Override
    @Synchronized
    public void hideGui() {
        if (dialog == null) {
            return;
        }
        this.guiSubscription.unsubscribe();
        this.dialog.dispose();
        this.dialog = null;
    }


    @Override
    public @NonNull Subscription addMessageListener(@NonNull MessageListener listener) {
        return listeners.addListener(listener);
    }

    private void onGuiMessage(@NonNull String message) {
        if (message.equals(QUIT_COMMAND)) {
            inputReader.shouldExit = true;
            try {
                final var robot = new Robot();
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
            } catch (AWTException ignored) {
                //
            }
        } else {
            sendMessage(message);
        }
    }

    private void sendMessage(@NonNull String message) {
        final MessageContext ctx = MessageContext.builder()
                                                 .messageFromMe(false)
                                                 .content(message)
                                                 .rawPayload(message)
                                                 .messageOwner(LOCAL_USER)
                                                 .receptionTime(Instant.now())
                                                 .channelInfo(CONSOLE_CHANNEL_INFO)
                                                 .build();
        listeners.warnListeners(l -> l.onMessage(ctx));
    }


    private class InputReader extends Looper {

        private BufferedReader reader;

        public boolean shouldExit = false;

        @Override
        protected void beforeLooping() {
            super.beforeLooping();
            reader = new BufferedReader(new InputStreamReader(System.in));
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
        protected @NonNull IterationCommand performOneIteration() throws IOException {
            final String line = getNextNotBlankLine();
            if (line.equals(QUIT_COMMAND)) {
                return IterationCommand.STOP;
            }
            sendMessage(line);
            return IterationCommand.CONTINUE;
        }

        public String getNextNotBlankLine() throws IOException {
            while (true) {
                final String line = reader.readLine();
                if (line == null || shouldExit) {
                    return QUIT_COMMAND;
                }
                if (!line.isBlank()) {
                    return line.trim();
                }
            }
        }
    }

}
