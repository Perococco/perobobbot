package perobobbot.localio;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.common.lang.*;
import perobobbot.common.lang.fp.Function1;
import perobobbot.localio.swing.InputPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.Scanner;

@RequiredArgsConstructor
public class Local implements LocalIO {

    private final @NonNull ApplicationCloser applicationCloser;

    private final @NonNull Listeners<MessageListener> listeners = new Listeners<>();

    private final @NonNull InputReader inputReader = new InputReader();

    private JFrame dialog = null;
    private SubscriptionHolder guiSubscription = new SubscriptionHolder();

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
    public void print(@NonNull String channel, @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder) {
        final String message = messageBuilder.apply((DispatchContext) Instant::now);
        switch (channel) {
            case CONSOLE:
                System.out.println(message);
                break;
            case GUI:
        }
    }

    @Override
    public @NonNull Subscription addMessageListener(@NonNull MessageListener listener) {
        return listeners.addListener(listener);
    }

    private void onGuiMessage(@NonNull String message) {
        if (message.equals("exit")) {
            inputReader.requestStop();
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
            if (line.equals("exit")) {
                return IterationCommand.STOP;
            }
            sendMessage(line);
            return IterationCommand.CONTINUE;
        }

        public String getNextNotBlankLine() throws IOException {
            while (true) {
                final String line = reader.readLine().trim();
                if (!line.isBlank()) {
                    return line;
                }
            }
        }
    }

}
