package perobobbot.localio;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.context.annotation.Bean;
import perobobbot.chat.core.DispatchSlip;
import perobobbot.lang.*;
import perobobbot.lang.fp.Function1;
import perobobbot.localio.swing.InputPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class LocalIO implements LocalChat {

    private final @NonNull Bot bot;

    private final @NonNull LocalSender localSender;

    @Override
    public @NonNull CompletionStage<? extends DispatchSlip> send(@NonNull Function1<? super DispatchContext, ? extends String> messageBuilder) {
        return localSender.send(bot,messageBuilder);
    }


//    @Override
//    public void disable() {
//        hideGui();
//        inputReader.requestStop();
//    }
//
//    @Override
//    @Synchronized
//    public void showGui() {
//        if (GraphicsEnvironment.isHeadless() || dialog != null) {
//            return;
//        }
//        final InputPanel inputPanel = new InputPanel();
//        this.guiSubscription.replaceWith(() -> inputPanel.addListener(this::onGuiMessage));
//        this.dialog = new JFrame("Local Command");
//        this.dialog.getContentPane().add(inputPanel);
//        this.dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        this.dialog.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosed(WindowEvent e) {
//                guiSubscription.unsubscribe();
//                dialog = null;
//            }
//        });
//        this.dialog.pack();
//        this.dialog.setVisible(true);
//        this.dialog.setAlwaysOnTop(true);
//        this.dialog.toFront();
//    }
//
//    @Override
//    @Synchronized
//    public void hideGui() {
//        if (dialog == null) {
//            return;
//        }
//        this.guiSubscription.unsubscribe();
//        this.dialog.dispose();
//        this.dialog = null;
//    }
//
//    private void onGuiMessage(@NonNull String message) {
//        if (message.equals(QUIT_COMMAND)) {
//            inputReader.shouldExit = true;
//            try {
//                final var robot = new Robot();
//                robot.keyPress(KeyEvent.VK_ENTER);
//                robot.keyRelease(KeyEvent.VK_ENTER);
//            } catch (AWTException ignored) {
//                //
//            }
//        } else {
//            sendMessage(message);
//        }
//    }
//
//    private void sendMessage(@NonNull String message) {
//        final MessageContext ctx = MessageContext.builder()
//                                                 .build(b)
//                                                 .messageFromMe(false)
//                                                 .content(message)
//                                                 .rawPayload(message)
//                                                 .messageOwner(LOCAL_USER)
//                                                 .receptionTime(Instant.now())
//                                                 .channelInfo(CONSOLE_CHANNEL_INFO)
//                                                 .build();
//        listeners.warnListeners(l -> l.onMessage(ctx));
//    }



}
